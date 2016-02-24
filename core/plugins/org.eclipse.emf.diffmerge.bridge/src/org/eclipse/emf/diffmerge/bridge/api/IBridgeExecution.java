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
package org.eclipse.emf.diffmerge.bridge.api;

import org.eclipse.core.runtime.IStatus;


/**
 * The result of the execution of an IBridge that maps target data elements to
 * causes of their presence defined in the source data.
 * @author Olivier Constant
 */
public interface IBridgeExecution {
  
  /**
   * Return the target data element that is associated to the given cause
   * @param cause_p a non-null cause
   * @return an object which is not null if the cause is applicable
   */
  <T> T get(ICause<?, T> cause_p);
  
  /**
   * Return the status of the execution
   * @return a status which is not null unless the execution is still ongoing
   */
  IStatus getStatus();
  
  /**
   * Return the optional trace that reflects this execution
   * @return a potentially null object
   */
  IBridgeTrace getTrace();
  
  
  /**
   * Modifiable extension of IBridgeExecution.
   */
  interface Editable extends IBridgeExecution {
    /**
     * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#getTrace()
     */
    IBridgeTrace.Editable getTrace();
    
    /**
     * Associate and register the given target data with the given cause
     * @param cause_p a non-null cause
     * @param target_p a non-null target data element
     */
    <T> void put(ICause<?, T> cause_p, T target_p);
  }
  
}
