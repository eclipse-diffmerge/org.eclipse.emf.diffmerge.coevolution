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
package org.eclipse.emf.diffmerge.bridge.operations;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.impl.IncrementalWrappingBridgeExecution;


/**
 * A bridge operation between data scopes for incremental wrapping bridges.
 * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge.Wrapping
 * @author Olivier Constant
 */
public class IncrementalWrappingBridgeOperation extends AbstractBridgeOperation {
  
  /** Whether the bridge operation is actually incremental, given its configuration */
  private final boolean _isActuallyIncremental;
  
  
  /**
   * Constructor
   * @param sourceDataSet_p the non-null source data set
   * @param targetDataSet_p the non-null target data set
   * @param bridge_p the non-null bridge to execute
   * @param execution_p a non-null execution for the bridge
   */
  public IncrementalWrappingBridgeOperation(Object sourceDataSet_p, Object targetDataSet_p,
      IIncrementalBridge.Wrapping<?,?,?> bridge_p, IBridgeExecution execution_p) {
    super(sourceDataSet_p, targetDataSet_p, bridge_p, execution_p);
    _isActuallyIncremental = isActuallyIncremental(bridge_p, targetDataSet_p, execution_p);
    getBridgeExecution().setActuallyIncremental(_isActuallyIncremental);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.operations.AbstractBridgeOperation#getBridge()
   */
  @Override
  @SuppressWarnings("rawtypes")
  public IIncrementalBridge.Wrapping<?,?,?> getBridge() {
    return (IIncrementalBridge.Wrapping)super.getBridge();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.operations.AbstractBridgeOperation#getBridgeExecution()
   */
  @Override
  public IncrementalWrappingBridgeExecution getBridgeExecution() {
    return (IncrementalWrappingBridgeExecution)super.getBridgeExecution();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.impl.helpers.AbstractExpensiveOperation#getWorkAmount()
   */
  @Override
  @SuppressWarnings("unchecked")
  protected int getWorkAmount() {
    return ((IBridge<Object, Object>)getBridge()).getWorkAmount(
        getSourceDataSet(), getTargetDataSet());
  }
  
  /**
   * Return whether the operation is actually incremental given its configuration
   */
  public boolean isActuallyIncremental() {
    return _isActuallyIncremental;
  }
  
  /**
   * Return whether the operation should actually be incremental, given the
   * provided configuration
   * @param bridge_p a non-null bridge
   * @param targetDataSet_p a non-null target data set
   * @param execution_p a non-null execution
   */
  @SuppressWarnings("unchecked")
  protected boolean isActuallyIncremental(IIncrementalBridge<?,?,?> bridge_p,
      Object targetDataSet_p, IBridgeExecution execution_p) {
    boolean result = false;
    if (execution_p instanceof IIncrementalBridgeExecution) {
      IIncrementalBridgeExecution execution = (IIncrementalBridgeExecution)execution_p;
      result = execution.canBeIncremental() &&
          !((IIncrementalBridge<?,Object,?>)bridge_p).isEmpty(targetDataSet_p);
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.util.IExpensiveOperation#run()
   */
  @SuppressWarnings("unchecked")
  public IStatus run() {
    IStatus result;
    IIncrementalBridge.Wrapping<?,?,?> bridge = getBridge();
    IBridge<?,?> transformationBridge = bridge.getTransformationBridge();
    if (_isActuallyIncremental) {
      getMonitor().worked(1);
      // Execute non-incremental bridge from source to intermediate data set based on
      // the bridge execution provided by the bridge
      Object intermediateDataSet =
          ((IIncrementalBridge<Object, Object, Object>)bridge).createIntermediateDataSet(
              getSourceDataSet(), getTargetDataSet());
      IBridgeExecution transformationExecution = ((IBridge<Object, Object>)transformationBridge).executeOn(
          getSourceDataSet(), intermediateDataSet, getBridgeExecution().getTransformationExecution(), getMonitor());
      if (transformationExecution.getStatus().isOK()) {
        // Transformation OK: proceed with merge
        result = ((IIncrementalBridge<Object,Object,Object>)bridge).merge(
            intermediateDataSet, getTargetDataSet(), getBridgeExecution(), getMonitor());
      } else {
        // Transformation KO: return transformation status
        result = transformationExecution.getStatus();
      }
    } else {
      // Cannot be incremental in that situation:
      // Perform transformation only, skipping the merge phase
      IBridgeExecution execution = ((IBridge<Object, Object>)transformationBridge).executeOn(
          getSourceDataSet(), getTargetDataSet(), getBridgeExecution().getTransformationExecution(), getMonitor());
      result = execution.getStatus();
    }
    return result;
  }
  
}