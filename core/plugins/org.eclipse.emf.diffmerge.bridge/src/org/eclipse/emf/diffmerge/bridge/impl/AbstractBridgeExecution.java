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
package org.eclipse.emf.diffmerge.bridge.impl;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;
import org.eclipse.emf.diffmerge.bridge.util.structures.Tuple2;


/**
 * A base implementation of IBridgeExecution.
 * @author Olivier Constant
 */
public abstract class AbstractBridgeExecution implements IBridgeExecution.Editable {
  
  /** The status of the execution */
  private IStatus _status;
  
  
  /**
   * Constructor
   */
  public AbstractBridgeExecution() {
    _status = Status.OK_STATUS; // Arbitrary
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#getStatus()
   */
  public IStatus getStatus() {
    return _status;
  }
  
  /**
   * Handle the fact that the given cause is not supported by this execution
   * @param cause_p a non-null cause
   */
  protected void handleWrongCause(ICause<?, ?> cause_p) {
    throw new IllegalArgumentException("Wrong type of cause: " + cause_p); //$NON-NLS-1$
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution.Editable#put(org.eclipse.emf.diffmerge.bridge.api.ICause, java.lang.Object)
   */
  public <T> void put(ICause<?, T> cause_p, T target_p) {
    putInTrace(cause_p, target_p);
  }
  
  /**
   * Associate and register the given target data elements with the given cause
   * in the execution trace, if any
   * @param cause_p a non-null cause
   * @param target_p a non-null target data element
   */
  protected <T> void putInTrace(ICause<?, T> cause_p, T target_p) {
    IBridgeTrace.Editable trace = getTrace();
    if (trace != null) {
      // Decompose pure structures
      if (target_p instanceof IPureStructure<?> && cause_p instanceof ICause.Symbolic<?,?>) {
        Collection<? extends Tuple2<?,?>> contents = ((IPureStructure<?>)target_p).getContents();
        for (Tuple2<?,?> slotAndValue : contents) {
          StructureBasedCause structCause = new StructureBasedCause(
              (ICause.Symbolic<?,?>)cause_p, slotAndValue.get1());
          trace.putCause(structCause, slotAndValue.get2());
        }
      } else {
        trace.putCause(cause_p, target_p);
      }
    }
  }
  
  /**
   * Set the status of this execution
   * @param newStatus_p a potentially null status
   */
  public void setStatus(IStatus newStatus_p) {
    _status = newStatus_p;
  }
  
}
