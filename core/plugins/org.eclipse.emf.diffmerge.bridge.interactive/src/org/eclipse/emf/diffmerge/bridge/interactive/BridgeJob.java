/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.emf.diffmerge.bridge.interactive;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.interactive.util.ResourceUtil;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage;
import org.eclipse.emf.diffmerge.impl.scopes.FragmentedModelScope;
import org.eclipse.emf.diffmerge.ui.util.MiscUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.progress.IProgressConstants;


/**
 * A Job which executes a bridge from a given source data set to an EMF resource
 * of a given URI.
 * @param <SD> the type of the source data set
 * @author Olivier Constant
 */
public abstract class BridgeJob<SD> extends Job {
  
  /**The bridge job logger */
  protected static final Logger logger = Logger.getLogger(BridgeJob.class);
  
  /** The model size beyond which the logger shall be disabled */
  protected static final long LOGGING_THRESHOLD =  20971520; //20MB
  
  /** The non-null source data set */
  protected final SD _sourceDataSet;
  
  /** The non-null URI of the target resource */
  protected final URI _targetURI;
  
  /** The non-null resource set for the target model */
  protected ResourceSet _targetResourceSet;
  
  /**
   * Constructor
   * @param jobName_p an optional name for the job
   * @param sourceDataSet_p the non-null source data set
   * @param targetURI_p the non-null URI of the target resource
   */
  public BridgeJob(String jobName_p, SD sourceDataSet_p, URI targetURI_p) {
	  super(jobName_p == null? Messages.BridgeJob_DefaultName: jobName_p);
	  _sourceDataSet = sourceDataSet_p;
	  _targetURI = targetURI_p;
	  _targetResourceSet = initializeTargetResourceSet();
	  setUser(true);
	  setupLogger();
  }

  /**
   * Return the bridge
   * @return a non-null object
   */
  protected abstract EMFInteractiveBridge<SD, IEditableModelScope> getBridge();
	
  /**
   * Return the resource of the given URI in the target resource set,
   * creating it if necessary
   * @param uri_p a non-null URI
   * @return a non-null resource
   */
  protected Resource getCreateTargetResource(URI uri_p) {
    ResourceSet rs = getTargetResourceSet();
    Resource result = ResourceUtil.getCreateResourceForUri(uri_p, rs);
    ResourceUtil.ensureLoaded(result);
    return result;
  }
  
  /**
   * Return the resource of the given URI in the given resource set,
   * creating it if necessary
   * @param uri_p a non-null URI
   * @return a non-null resource
   */
  protected Resource getCreateTraceResource(URI uri_p) {
    return getCreateTargetResource(uri_p);
  }
  
  /**
   * Return the editing domain of the target resource set, if any
   * @return a potentially null editing domain
   */
  protected EditingDomain getTargetEditingDomain() {
    EditingDomain result = null;
    ResourceSet rs = getTargetResourceSet();
    result = AdapterFactoryEditingDomain.getEditingDomainFor(rs);
    return result;
  }
  
  /**
   * Return a resource set for handling the target model
   * @return a non-null resource set
   */
  protected final ResourceSet getTargetResourceSet() {
    return _targetResourceSet;
  }
  
  /**
   * Return a target model scope that corresponds to the given resource
   * @param resource_p a non-null resource
   * @return a non-null model scope
   */
  protected IEditableModelScope getTargetScope(Resource resource_p) {
    return new FragmentedModelScope(resource_p, false);
  }
  
  /**
   * Return the trace contained in the given trace resource, if any
   * @param traceResource_p a non-null resource
   * @return a potentially null trace
   */
  protected IBridgeTrace getTrace(Resource traceResource_p) {
    IBridgeTrace result = null;
    if (!traceResource_p.getContents().isEmpty()) {
      EObject root = traceResource_p.getAllContents().next();
      if (root instanceof IBridgeTrace)
        result = (IBridgeTrace)root;
    }
    return result;
  }
  
  /**
   * Return the URI for the trace resource of the bridge
   * @return a non-null URI
   */
  protected URI getTraceURI() {
    return _targetURI.appendFileExtension(BridgetracesPackage.eNAME);
  }
  
  /**
   * Perform the interactive part of the bridge execution process
   * @param bridge_p the non-null bridge
   * @param execution_p the non-null current bridge execution
   * @param monitor_p a non-null progress monitor
   */
  protected IStatus handleInteractivePart(IIncrementalBridge<?,?,?> bridge_p,
      IIncrementalBridgeExecution execution_p, SubMonitor monitor_p) {
    monitor_p.subTask(Messages.BridgeJob_Step_InteractiveUpdate);
    logger.info(Messages.BridgeLogger_InteractiveMergeStepMessage);
    // Interactive merge
    IStatus result = bridge_p.mergeInteractively(execution_p, monitor_p);
    if (execution_p instanceof IIncrementalBridgeExecution.Editable)
      ((IIncrementalBridgeExecution.Editable)execution_p).setInteractiveMergeData(null);
    return result;
  }
  
  /**
   * Perform the main part of the bridge execution process
   * @param bridge_p the non-null bridge to execute
   * @param targetResource_p the non-null target resource
   * @param existingTrace_p the optional existing trace
   * @param monitor_p a non-null monitor
   * @return a non-null bridge execution
   */
  protected IIncrementalBridgeExecution handleMainPart(
      final IIncrementalBridge<SD, IEditableModelScope, ?> bridge_p,
      Resource targetResource_p, final IBridgeTrace existingTrace_p,
      SubMonitor monitor_p) {
    final SubMonitor bridgeMonitor = monitor_p.newChild(8);
    final IEditableModelScope targetScope = getTargetScope(targetResource_p);
    EditingDomain domain = getTargetEditingDomain();
    final IIncrementalBridgeExecution[] executionWrapper = new IIncrementalBridgeExecution[1];
    Runnable runnable = new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        IIncrementalBridgeExecution localExecution = bridge_p.executeOn(
            _sourceDataSet, targetScope, null, existingTrace_p, true, bridgeMonitor);
        executionWrapper[0] = localExecution;
      }
    };
    MiscUtil.execute(domain, getName(), runnable, true);
    IIncrementalBridgeExecution result = executionWrapper[0];
    bridgeMonitor.done();
    return result;
  }
  
  /**
   * Return a resource set for handling the target model.
   * This method must only be called once.
   * @return a non-null resource set
   */
  protected ResourceSet initializeTargetResourceSet() {
    return new ResourceSetImpl();
  }
  
  /**
   * Return whether this job is modal (the user is waiting) or not
   */
  protected boolean isModal() {
    boolean result = false;
    Boolean modalProp = (Boolean)getProperty(IProgressConstants.PROPERTY_IN_DIALOG);
    if (modalProp != null)
      result = modalProp.booleanValue();
    return result;
  }
  
  /**
   * Return whether the target must be saved and closed when done
   */
  protected boolean isSaveAndCloseTarget() {
    return true;
  }
  
  /**
   * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IStatus run(final IProgressMonitor monitor_p) {
    // Monitor usage
    final int workAmount = 10;
    SubMonitor monitor;
    if (monitor_p instanceof SubMonitor)
      monitor = (SubMonitor)monitor_p;
    else
      monitor = SubMonitor.convert(monitor_p, getName(), workAmount);
    monitor.subTask(Messages.BridgeJob_Step_SetUp);
    // Target data set definition
    final Resource targetResource = getCreateTargetResource(_targetURI);
    // Trace definition
    URI traceURI = getTraceURI();
    final Resource traceResource = getCreateTraceResource(traceURI);
    monitor.worked(1);
    // Trace and target scope
    monitor.subTask(Messages.BridgeJob_Step_Execution);
    final IBridgeTrace existingTrace = getTrace(traceResource);
    // Execution
    final EMFInteractiveBridge<SD, IEditableModelScope> bridge = getBridge();
    IIncrementalBridgeExecution execution =
        handleMainPart(bridge, targetResource, existingTrace, monitor);
    // User interactions and completion
    IStatus result = handleInteractivePart(bridge, execution, monitor);
    // Saving
    if (result.isOK())
      saveAndClose(execution, targetResource, traceResource, monitor_p);
    monitor_p.done();
    return result;
  }
  
  /**
   * Save and close the give resource according to the given execution
   * @param execution_p the non-null ongoing execution
   * @param targetResource_p the non-null target resource
   * @param traceResource_p the non-null trace resource
   * @param monitor_p a non-null progress monitor
   */
  protected void saveAndClose(IIncrementalBridgeExecution execution_p, Resource targetResource_p,
      Resource traceResource_p, IProgressMonitor monitor_p) {
    // Save and unload
    monitor_p.subTask(Messages.BridgeJob_Step_Completion);
    monitor_p.worked(1);
    if (!execution_p.isActuallyIncremental())
      setTrace(traceResource_p, execution_p.getTrace());
    if (!traceResource_p.getContents().isEmpty())
      ResourceUtil.makePersistent(traceResource_p);
    ResourceUtil.closeResource(traceResource_p);
    if (isSaveAndCloseTarget()) {
      ResourceUtil.makePersistent(targetResource_p);
      ResourceUtil.closeResource(targetResource_p);
    }
  }
  
  /**
   * Set the given trace in the given trace resource
   * @param traceResource_p a non-null resource
   * @param trace_p a non-null trace
   */
  protected void setTrace(Resource traceResource_p, IBridgeTrace trace_p) {
    if (trace_p instanceof EObject) {
      traceResource_p.getContents().clear();
      traceResource_p.getContents().add((EObject)trace_p);
    }
  }
  
  /**
   * Enable/disable logging facility according to source data set size.
   */
  protected void setupLogger() {
    long dataSetSize = 0;
    if (_sourceDataSet instanceof Resource)
      dataSetSize = ResourceUtil.getFileForResource((Resource) _sourceDataSet).getLocation().toFile().length();
    else if (_sourceDataSet instanceof EObject) {
      IFile fileForResource = ResourceUtil.getFileForResource(((EObject) _sourceDataSet).eResource());
      if (fileForResource != null)
        dataSetSize = fileForResource.getLocation().toFile().length();
    }
  	if (dataSetSize < LOGGING_THRESHOLD) {
  		LogManager.resetConfiguration();
  		Properties properties = new Properties();
  		properties.setProperty(Messages.BridgeLoggerConfig_LoggerKey, Messages.BridgeLoggerConfig_LoggerValue);
  		properties.setProperty(Messages.BridgeLoggerConfig_AppenderKey, Messages.BridgeLoggerConfig_AppenderValue);
  		properties.setProperty(Messages.BridgeLoggerConfig_LayoutKey, Messages.BridgeLoggerConfig_LayoutValue);
  		properties.setProperty(Messages.BridgeLoggerConfig_TresholdKey, Messages.BridgeLoggerConfig_TresholdValue);
  		properties.setProperty(Messages.BridgeLoggerConfig_ConversionPatternKey,	Messages.BridgeLoggerConfig_ConversionPatternValue);
  		PropertyConfigurator.configure(properties);
  	} else {
  		logger.warn(Messages.BridgeLoggerConfig_DisabledLoggerWarning);
  		LogManager.resetConfiguration();
  		LogManager.getRootLogger().setLevel(Level.OFF);
  	}
  }
  
}
