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
package org.eclipse.emf.diffmerge.bridge.impl;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;


/**
 * An execution for incremental wrapping bridges.
 * @see IIncrementalBridgeExecution
 * @author Olivier Constant
 */
public class IncrementalWrappingBridgeExecution extends AbstractBridgeExecution
implements IIncrementalBridgeExecution.Editable {
  
  /** The non-null bridge execution for the non-incremental transformation part of the execution */
  private final IBridgeExecution.Editable _transformationExecution;
  
  /** Whether the interactive merge phase must be deferred, if any */
  private boolean _mustDeferInteractiveMerge;
  
  /** The optional reference bridge trace */
  private IBridgeTrace _referenceTrace;
  
  /** The optional context data for the interactive merge phase */
  private Object _interactiveMergeData;
  
  /** Whether the transformation is actually incremental */
  private boolean _isActuallyIncremental;
  
  
  /**
   * Constructor
   * @param transformationExecution_p a non-null bridge execution for the non-incremental transformation part of the execution
   */
  public IncrementalWrappingBridgeExecution(IBridgeExecution.Editable transformationExecution_p) {
    super();
    _transformationExecution = transformationExecution_p;
    _mustDeferInteractiveMerge = false;
    _referenceTrace = null;
    _isActuallyIncremental = false;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution#canBeIncremental()
   */
  public boolean canBeIncremental() {
    return getTrace() != null && getReferenceTrace() != null;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#get(org.eclipse.emf.diffmerge.bridge.api.ICause)
   */
  public <T> T get(ICause<?, T> cause_p) {
    return getTransformationExecution().get(cause_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution#getInteractiveMergeData()
   */
  public Object getInteractiveMergeData() {
    return _interactiveMergeData;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution#getReferenceTrace()
   */
  public IBridgeTrace getReferenceTrace() {
    return _referenceTrace;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution.Editable#getTrace()
   */
  public IBridgeTrace.Editable getTrace() {
    return getTransformationExecution().getTrace();
  }
  
  /**
   * Return the bridge execution for the non-incremental transformation part of the execution
   * @return a non-null object
   */
  public IBridgeExecution.Editable getTransformationExecution() {
    return _transformationExecution;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution#isActuallyIncremental()
   */
  public boolean isActuallyIncremental() {
    return _isActuallyIncremental;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution#mustDeferInteractiveMerge()
   */
  public boolean mustDeferInteractiveMerge() {
    return _mustDeferInteractiveMerge;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeExecution#put(org.eclipse.emf.diffmerge.bridge.api.ICause, java.lang.Object)
   */
  @Override
  public <T> void put(ICause<?, T> cause_p, T target_p) {
    getTransformationExecution().put(cause_p, target_p);
  }
  
  /**
   * Set whether the transformation is actually incremental given the execution context
   * @param isIncremental_p whether it is incremental
   */
  public void setActuallyIncremental(boolean isIncremental_p) {
    _isActuallyIncremental = isIncremental_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution.Editable#setDeferInteractiveMerge(boolean)
   */
  public void setDeferInteractiveMerge(boolean deferInteractiveMerge_p) {
    _mustDeferInteractiveMerge = deferInteractiveMerge_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution.Editable#setInteractiveMergeData(java.lang.Object)
   */
  public void setInteractiveMergeData(Object mergeData_p) {
    _interactiveMergeData = mergeData_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution.Editable#setReferenceTrace(org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace)
   */
  public void setReferenceTrace(IBridgeTrace referenceTrace_p) {
    _referenceTrace = referenceTrace_p;
  }
  
}
