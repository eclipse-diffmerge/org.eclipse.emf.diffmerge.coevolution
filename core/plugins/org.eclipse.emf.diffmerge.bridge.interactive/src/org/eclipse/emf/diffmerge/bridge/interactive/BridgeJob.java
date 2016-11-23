/**
 * <copyright>
 * 
 * Copyright (c) 2014-2016 Thales Global Services S.A.S.
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
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.progress.IProgressConstants;


/**
 * A Job which executes a bridge from a given source data set to an EMF resource
 * of a given URI.
 * @param <SD> the type of the source data set
 * @author Olivier Constant
 */
public abstract class BridgeJob<SD> extends Job {
  
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
   * Perform the deferrable part of the bridge process
   * @param bridge_p the non-null bridge
   * @param execution_p the non-null current bridge execution
   * @param targetResource_p the non-null target resource
   * @param traceResource_p the non-null trace resource
   * @param monitor_p a non-null progress monitor
   */
  protected void handleDeferrablePart(IIncrementalBridge<?,?,?> bridge_p,
      final IIncrementalBridgeExecution execution_p, Resource targetResource_p,
      Resource traceResource_p, final SubMonitor monitor_p) {
    monitor_p.subTask(Messages.BridgeJob_Step_InteractiveUpdate);
    // Defining the remaining part of the bridge process in a deferrable action
    DeferredBridgeExecutionAction deferrableAction = new DeferredBridgeExecutionAction(
        bridge_p, execution_p, targetResource_p, traceResource_p, isSaveAndCloseTarget(),
        monitor_p);
    setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
    setProperty(IProgressConstants.ACTION_PROPERTY, deferrableAction);
    if (isModal()) {
      // The user has waited: immediate execution
      deferrableAction.run();
      IStatus status = deferrableAction.getStatus();
      if (status != null) {
        if (status.getSeverity() == IStatus.CANCEL) {
          deferrableAction.dispose();
        } else if (status.getSeverity() == IStatus.INFO) {
          // Still ongoing: show progress view if possible
          final Display display = Display.getDefault();
          display.syncExec(new Runnable() {
            /**
             * @see java.lang.Runnable#run()
             */
            public void run() {
              try {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(
                    IProgressConstants.PROGRESS_VIEW_ID);
              } catch (Exception e) {
                // Proceed
              }
            }
          });
        }
      }
    }
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
    final SubMonitor bridgeMonitor = monitor.newChild(8);
    final IBridgeTrace existingTrace = getTrace(traceResource);
    final IEditableModelScope targetScope = getTargetScope(targetResource);
    // Execution
    final EMFInteractiveBridge<SD, IEditableModelScope> bridge = getBridge();
    EditingDomain domain = getTargetEditingDomain();
    final IIncrementalBridgeExecution[] executionWrapper = new IIncrementalBridgeExecution[1];
    Runnable runnable = new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        IIncrementalBridgeExecution localExecution = bridge.executeOn(
            _sourceDataSet, targetScope, null, existingTrace, true, bridgeMonitor);
        executionWrapper[0] = localExecution;
      }
    };
    MiscUtil.execute(domain, getName(), runnable, true);
    IIncrementalBridgeExecution execution = executionWrapper[0];
    bridgeMonitor.done();
    // User interactions and completion
    handleDeferrablePart(bridge, execution, targetResource, traceResource, monitor);
    return execution.getStatus();
  }
  
  /**
   * Set the given trace in the given trace resource
   * @param traceResource_p a non-null resource
   * @param trace_p a non-null trace
   */
  protected static void setTrace(Resource traceResource_p, IBridgeTrace trace_p) {
    if (trace_p instanceof EObject) {
      traceResource_p.getContents().clear();
      traceResource_p.getContents().add((EObject)trace_p);
    }
  }
  
  
  /**
   * An action that triggers the deferred completion of the execution of a bridge.
   */
  protected static class DeferredBridgeExecutionAction extends Action
  implements ActionFactory.IWorkbenchAction {
    /** The initially non-null bridge */
    private IIncrementalBridge<?,?,?> _bridge;
    /** The initially non-null ongoing execution */
    private IIncrementalBridgeExecution _execution;
    /** The initially non-null target resource */
    private Resource _targetResource;
    /** The initially non-null trace resource */
    private Resource _traceResource;
    /** Whether the target must be saved and closed when done */
    private boolean _isSaveAndCloseTarget;
    /** The initially null execution status */
    private IStatus _status;
    /** The initially non-null progress monitor */
    private SubMonitor _monitor;
    /**
     * Constructor
     * @param bridge_p the non-null bridge whose execution has to be completed
     * @param execution_p the non-null ongoing execution of the bridge
     * @param monitor_p a non-null progress monitor
     */
    public DeferredBridgeExecutionAction(IIncrementalBridge<?,?,?> bridge_p,
        IIncrementalBridgeExecution execution_p, Resource targetResource_p,
        Resource traceResource_p, boolean isSaveAndCloseTarget_p, SubMonitor monitor_p) {
      super(Messages.BridgeJob_ActionText);
      _bridge = bridge_p;
      _execution = execution_p;
      _targetResource = targetResource_p;
      _traceResource = traceResource_p;
      _isSaveAndCloseTarget = isSaveAndCloseTarget_p;
      _status = null;
      _monitor = monitor_p;
    }
    /**
     * @see org.eclipse.ui.actions.ActionFactory.IWorkbenchAction#dispose()
     */
    public void dispose() {
      if (_execution instanceof IIncrementalBridgeExecution.Editable)
        ((IIncrementalBridgeExecution.Editable)_execution).setInteractiveMergeData(null);
      _bridge = null;
      _execution = null;
      _targetResource = null;
      _traceResource = null;
      _monitor = null;
      _status = null;
      setEnabled(false);
    }
    /**
     * Return the status of the execution of the action
     * @return a potentially null object
     */
    public IStatus getStatus() {
      return _status;
    }
    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
      // Interactive merge
      _status = _bridge.mergeInteractively(_execution, _monitor);
      if (_status.isOK()) {
        // Save and unload
        _monitor.subTask(Messages.BridgeJob_Step_Completion);
        _monitor.worked(1);
        if (!_execution.isActuallyIncremental())
          setTrace(_traceResource, _execution.getTrace());
        if (!_traceResource.getContents().isEmpty())
          ResourceUtil.makePersistent(_traceResource);
        ResourceUtil.closeResource(_traceResource);
        if (_isSaveAndCloseTarget) {
          ResourceUtil.makePersistent(_targetResource);
          ResourceUtil.closeResource(_targetResource);
        }
      }
      if (_status.isOK() || _status.getSeverity() == IStatus.CANCEL ||
          _status.getSeverity() == IStatus.INFO &&
              EMFInteractiveBridge.STATUS_SWITCH_TO_EDITOR.equals(_status.getMessage())) {
        _monitor.done();
        dispose();
      }
    }
  }
  
}
