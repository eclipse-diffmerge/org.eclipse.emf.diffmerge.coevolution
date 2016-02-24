/**
 * <copyright>
 * 
 * Copyright (c) 2014 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.impl.emf;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.impl.IncrementalWrappingBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.operations.IncrementalWrappingBridgeOperation;


/**
 * A partial implementation of IIncrementalBridge.Wrapping.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @param <ID> the type of the intermediate target data set
 * @see IIncrementalBridge
 * @author Olivier Constant
 */
public abstract class AbstractWrappingIncrementalBridge<SD, TD, ID>
implements IIncrementalBridge.Wrapping<SD, TD, ID> {
  
  /** The non-null non-incremental bridge */
  private final IBridge<SD, TD> _transformationBridge;
  
  
  /**
   * Constructor
   * @param bridge_p a non-null bridge acting as the non-incremental bridge
   */
  public AbstractWrappingIncrementalBridge(IBridge<SD, TD> bridge_p) {
    super();
    _transformationBridge = bridge_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#createExecution(org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace.Editable)
   */
  public IIncrementalBridgeExecution.Editable createExecution(IBridgeTrace.Editable trace_p) {
    IBridgeTrace.Editable newTrace = trace_p != null? trace_p: createTrace();
    IBridgeExecution.Editable transformationExecution = getTransformationBridge().createExecution(newTrace);
    return new IncrementalWrappingBridgeExecution(transformationExecution);
  }
  
  /**
   * Create and return an empty trace
   * @return a non-null object
   */
  protected abstract IBridgeTrace.Editable createTrace();
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#executeOn(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IIncrementalBridgeExecution executeOn(SD sourceDataSet_p,
      TD targetDataSet_p, IBridgeExecution execution_p, IProgressMonitor monitor_p) {
    return executeOn(sourceDataSet_p, targetDataSet_p, execution_p, null, false, monitor_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#executeOn(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution, org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace, boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IIncrementalBridgeExecution executeOn(SD sourceDataSet_p, TD targetDataSet_p,
      IBridgeExecution execution_p, IBridgeTrace referenceTrace_p, boolean deferInteractiveMerge_p,
      IProgressMonitor monitor_p) {
    IIncrementalBridgeExecution.Editable execution;
    if (execution_p instanceof IIncrementalBridgeExecution.Editable)
      execution = (IIncrementalBridgeExecution.Editable)execution_p;
    else if (execution_p instanceof IBridgeExecution.Editable)
      execution = createExecution(((IBridgeExecution.Editable)execution_p).getTrace());
    else
      execution = createExecution(null);
    execution.setReferenceTrace(referenceTrace_p);
    execution.setDeferInteractiveMerge(deferInteractiveMerge_p);
    IncrementalWrappingBridgeOperation operation = new IncrementalWrappingBridgeOperation(
        sourceDataSet_p, targetDataSet_p, this, execution);
    operation.run(monitor_p);
    return operation.getBridgeExecution();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge.Wrapping#getTransformationBridge()
   */
  public IBridge<SD, TD> getTransformationBridge() {
    return _transformationBridge;
  }
  
}
