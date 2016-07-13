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
package org.eclipse.emf.diffmerge.bridge.incremental;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.api.IComparison;
import org.eclipse.emf.diffmerge.api.IDiffPolicy;
import org.eclipse.emf.diffmerge.api.IMatch;
import org.eclipse.emf.diffmerge.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.api.IMergePolicy;
import org.eclipse.emf.diffmerge.api.IMergeSelector;
import org.eclipse.emf.diffmerge.api.Role;
import org.eclipse.emf.diffmerge.api.diff.IElementPresence;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.impl.emf.AbstractWrappingIncrementalEMFBridge;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesFactory;
import org.eclipse.emf.diffmerge.bridge.util.INormalizableModelScope;
import org.eclipse.emf.diffmerge.diffdata.EComparison;
import org.eclipse.emf.diffmerge.diffdata.impl.EComparisonImpl;
import org.eclipse.emf.ecore.EObject;


/**
 * An implementation of IIncrementalBridge for EMF data sets based on a trace model
 * and the EMF Diff/Merge engine. Update is realized via a merge operation which
 * is composed of an automatic phase optionally followed by an interactive phase.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IIncrementalBridge
 * @author Olivier Constant
 */
public class EMFIncrementalBridge<SD, TD extends IEditableModelScope>
extends AbstractWrappingIncrementalEMFBridge<SD, TD> {
  
  /** The comparison role that corresponds to the target data set of the bridge */
  public static final Role TARGET_DATA_ROLE = Role.TARGET;
  
  /** The optional diff policy for updates (null for default) */
  private final IDiffPolicy _diffPolicy;
  
  /** The optional merge policy for updates (null for default) */
  private final IMergePolicy _mergePolicy;
  
  /** The optional merge selector for non-interactive updates (null for fully interactive) */
  private final IMergeSelector _mergeSelector;
  
  
  /**
   * Constructor
   * @param bridge_p a non-null bridge acting as the non-incremental bridge
   * @param diffPolicy_p an optional diff policy for updates (null for default)
   * @param mergePolicy_p an optional merge policy for updates (null for default)
   * @param merger_p an optional merge selector for non-interactive updates
   *        (null for fully interactive) where (REFERENCE: created scope, TARGET: existing scope)
   */
  public EMFIncrementalBridge(IBridge<SD, TD> bridge_p, IDiffPolicy diffPolicy_p,
      IMergePolicy mergePolicy_p, IMergeSelector merger_p) {
    super(bridge_p);
    _diffPolicy = diffPolicy_p;
    _mergePolicy = mergePolicy_p;
    _mergeSelector = merger_p;
  }
  
  /**
   * Compare the given scopes based on the given traces and return the resulting comparison,
   * built as (REFERENCE: created scope, TARGET: existing scope) 
   * @param created_p a non-null model scope
   * @param existing_p a non-null model scope
   * @param createdTrace_p a non-null trace
   * @param existingTrace_p a non-null trace
   * @param monitor_p an optional progress monitor
   * @return a non-null comparison
   */
  protected EComparison compare(IEditableModelScope created_p, IEditableModelScope existing_p,
      IBridgeTrace createdTrace_p, IBridgeTrace existingTrace_p, IProgressMonitor monitor_p) {
    EComparison result = new EComparisonImpl(existing_p, created_p);
    IMatchPolicy matchPolicy = createMatchPolicy(
    		created_p, existing_p, createdTrace_p, existingTrace_p);
    result.compute(matchPolicy, getDiffPolicy(), getMergePolicy(), monitor_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.emf.AbstractWrappingIncrementalEMFBridge#createIntermediateDataSet(java.lang.Object, org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope)
   */
  @Override
  public IEditableModelScope createIntermediateDataSet(SD sourceDataSet_p, TD targetDataSet_p) {
    return new IntermediateModelScope(sourceDataSet_p, targetDataSet_p);
  }
  
  /**
   * Return a match policy for the given scopes based on the given traces
   * @param created_p a non-null model scope
   * @param existing_p a non-null model scope
   * @param createdTrace_p a non-null trace
   * @param existingTrace_p a non-null trace
   * @return a non-null object
   */
  protected IMatchPolicy createMatchPolicy(IEditableModelScope created_p, IEditableModelScope existing_p,
		  IBridgeTrace createdTrace_p, IBridgeTrace existingTrace_p) {
	  return new BridgeTraceBasedMatchPolicy(created_p, createdTrace_p, existingTrace_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.emf.AbstractWrappingIncrementalEMFBridge#createTrace()
   */
  @Override
  protected IBridgeTrace.Editable createTrace() {
    return BridgetracesFactory.eINSTANCE.createTrace();
  }
  
  /**
   * Return the optional diff policy used to achieve updates
   * @return a potentially null diff policy
   */
  protected IDiffPolicy getDiffPolicy() {
    return _diffPolicy;
  }
  
  /**
   * Return the optional merge policy used to achieve updates
   * @return a potentially null merge policy
   */
  protected IMergePolicy getMergePolicy() {
    return _mergePolicy;
  }
  
  /**
   * Return the optional merge selector for non-interactive updates (null for fully interactive)
   * @return a potentially null merge selector
   */
  protected IMergeSelector getMergeSelector() {
    return _mergeSelector;
  }
  
  /**
   * Handle the merged differences of the given comparison so as to update traces
   * @param comparison_p a non-null comparison
   * @param createdTrace_p a non-null object
   * @param existingTrace_p a non-null object
   */
  protected void handleMergedDifferences(IComparison comparison_p,
      IBridgeTrace createdTrace_p, IBridgeTrace existingTrace_p) {
    if (existingTrace_p instanceof IBridgeTrace.Editable) {
      IBridgeTrace.Editable existingTrace = (IBridgeTrace.Editable)existingTrace_p;
      for (IMatch match : comparison_p.getMapping().getContents()) {
        // Added/removed elements
        IElementPresence presence = match.getElementPresenceDifference();
        if (presence != null && presence.isMerged()) {
          if (presence.getMergeDestination() == TARGET_DATA_ROLE) {
            if (presence.getPresenceRole() == TARGET_DATA_ROLE) {
              // Removal //TODO Detect moves in additions and removals. What about a change of cause?
              EObject removedTarget = presence.getElement();
              existingTrace.removeTarget(removedTarget);
            } else {
              // Addition
              EObject generated = presence.getElementMatch().get(TARGET_DATA_ROLE.opposite());
              Object cause = createdTrace_p.getCause(generated);
              if (cause != null) {
                EObject addedTarget = presence.getElementMatch().get(TARGET_DATA_ROLE);
                existingTrace.putCause(cause, addedTarget);
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * Notify that the given intermediate data set has been filled with target data elements
   * @param intermediateDataSet_p a non-null model scope
   */
  protected void intermediateDataSetFilled(IEditableModelScope intermediateDataSet_p) {
    if (intermediateDataSet_p instanceof INormalizableModelScope)
      ((INormalizableModelScope)intermediateDataSet_p).normalize();
  }
  
  /**
   * Return whether the bridge must always be interactive if it is an update,
   * even though no modification of the target data set is expected
   */
  protected boolean isAlwaysInteractive() {
    return false;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#merge(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IStatus merge(IEditableModelScope created_p, TD existing_p,
      IIncrementalBridgeExecution execution_p, IProgressMonitor monitor_p) {
    IStatus result;
    intermediateDataSetFilled(created_p);
    IBridgeTrace createdTrace = execution_p.getTrace();
    IBridgeTrace existingTrace = execution_p.getReferenceTrace();
    EComparison comparison = compare(
        created_p, existing_p, createdTrace, existingTrace, monitor_p);
    ((IIncrementalBridgeExecution.Editable)execution_p).setInteractiveMergeData(comparison);
    result = mergeAutomatically(comparison, monitor_p);
    if (result.isOK() && !execution_p.mustDeferInteractiveMerge())
      result = mergeInteractively(execution_p, monitor_p);
    return result;
  }
  
  /**
   * Merge the given (REFERENCE: created, TARGET: existing) comparison automatically
   * and return the merged differences
   * @param comparison_p a non-null comparison
   * @param monitor_p an optional progress monitor
   * @return a non-null status
   */
  protected IStatus mergeAutomatically(
      EComparison comparison_p, IProgressMonitor monitor_p) {
    IMergeSelector merger = getMergeSelector();
    if (merger != null)
      comparison_p.merge(merger, true, monitor_p);
    return Status.OK_STATUS;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#mergeInteractively(org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IStatus mergeInteractively(IIncrementalBridgeExecution execution_p, IProgressMonitor monitor_p) {
    IStatus result = execution_p.getStatus();
    if (result.isOK()) {
      Object mergeData = execution_p.getInteractiveMergeData();
      if (mergeData instanceof EComparison) {
        EComparison comparison = (EComparison)mergeData;
        if (isAlwaysInteractive() || comparison.hasRemainingDifferences())
          result = mergeInteractively(comparison, monitor_p);
        if (result.isOK())
          handleMergedDifferences(comparison, execution_p.getTrace(), execution_p.getReferenceTrace());
      }
    }
    return result;
  }
  
  /**
   * Merge the given (REFERENCE: created, TARGET: existing) comparison interactively
   * @param comparison_p a non-null comparison
   * @return a non-null status
   */
  protected IStatus mergeInteractively(EComparison comparison_p, IProgressMonitor monitor_p) {
    // Do nothing by default
    return Status.OK_STATUS;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridge#getWorkAmount(java.lang.Object, java.lang.Object)
   */
  public int getWorkAmount(SD sourceDataSet_p, TD targetDataSet_p) {
    int result = getTransformationBridge().getWorkAmount(sourceDataSet_p, targetDataSet_p);
    result = Math.max(result, 0) + 2; // Compare and merge
    return result;
  }
  
}
