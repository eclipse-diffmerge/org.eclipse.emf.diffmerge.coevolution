/**
 * <copyright>
 * 
 * Copyright (c) 2015 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.integration.transposer;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeTraceExecution;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.kitalpha.transposer.transformation.context.TransformationKey;


/**
 * An execution for Transposer bridges.
 * @see TransposerBridge
 * @see IBridgeExecution
 */
public class TransposerBridgeExecution extends AbstractBridgeTraceExecution {
  
  /** The (initially null, then non-null after a call to setup(...))
   * Transposer context associated to this execution */
  protected TransposerBridgeContext _context;
  
  /** The (initially null, then non-null after a call to setup(...))
   * target scope associated to this execution */
  protected IEditableModelScope _targetScope;
  
  
  /**
   * Constructor
   * @param trace_p the optional trace that reflects this execution
   */
  public TransposerBridgeExecution(IBridgeTrace.Editable trace_p) {
    super(trace_p);
    _context = null;
    _targetScope = null;
  }
  
  /**
   * Add the given target data element into the given target data set, if feasible
   * @param targetDataSet_p a non-null object
   * @param target_p a non-null object
   * @return whether the operation succeeded
   */
  protected boolean addElementaryTarget(IEditableModelScope targetDataSet_p, Object target_p) {
    boolean result = false;
    if (target_p instanceof EObject) {
      EObject element = (EObject)target_p;
      if (element.eContainer() == null)
        result = targetDataSet_p.add(element);
    }
    return result;
  }
  
  /**
   * Add the given target data into the given target data set, if feasible
   * @param targetDataSet_p a non-null object
   * @param target_p a non-null object
   * @return whether the operation succeeded
   */
  protected boolean addTarget(IEditableModelScope targetDataSet_p, Object target_p) {
    // Decompose pure structures
    Collection<?> targetElements;
    if (target_p instanceof IPureStructure<?>) {
      targetElements = ((IPureStructure<?>)target_p).asCollection();
    } else {
      targetElements = Collections.singleton(target_p);
    }
    for (Object targetElement : targetElements) {
      boolean OK = addElementaryTarget(targetDataSet_p, targetElement);
      if (!OK) return false;
    }
    return true;
  }
  
  /**
   * Associate and register the given target data with the given transformation key as cause,
   * without reflecting this change on the Transposer context
   * @param key_p a non-null Transposer transformation key
   * @param target_p a non-null target data element
   */
  /*package*/ void doPut(TransformationKey key_p, Object target_p) {
    TransposerBridgeCause cause = new TransposerBridgeCause(key_p);
    doPut(cause, target_p);
  }
  
  /**
   * Associate and register the given target data with the given cause,
   * without notifying the Transposer context
   * @param cause_p a non-null cause
   * @param target_p a non-null target data element
   */
  protected void doPut(TransposerBridgeCause cause_p, Object target_p) {
    addTarget(getTargetScope(), target_p);
    super.put(cause_p, target_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#get(org.eclipse.emf.diffmerge.bridge.api.ICause)
   */
  @SuppressWarnings("unchecked")
  public <T> T get(ICause<?, T> cause_p) {
    T result = null;
    if (cause_p instanceof TransposerBridgeCause) {
      TransposerBridgeCause transposerBridgeCause = (TransposerBridgeCause)cause_p;
      result = (T)_context.get(transposerBridgeCause.getTransformationKey());
    }
    return result;
  }
  
  /**
   * Return the target scope
   * @return a non-null object
   */
  protected IEditableModelScope getTargetScope() {
    return _targetScope;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeExecution#put(org.eclipse.emf.diffmerge.bridge.api.ICause, java.lang.Object)
   */
  @Override
  public <T> void put(ICause<?, T> cause_p, T target_p) {
    if (cause_p instanceof TransposerBridgeCause) {
      TransposerBridgeCause transposerBridgeCause = (TransposerBridgeCause)cause_p;
      _context.doPut(transposerBridgeCause.getTransformationKey(), target_p);
      doPut(transposerBridgeCause, target_p);
    } else {
      handleWrongCause(cause_p);
    }
  }
  
  /**
   * Set up this execution with the given target scope and Transposer context
   * @param targetScope_p a non-null object
   * @param context_p a non-null object
   */
  /*package*/ void setup(IEditableModelScope targetScope_p, TransposerBridgeContext context_p) {
    _targetScope = targetScope_p;
    _context = context_p;
  }
  
}
