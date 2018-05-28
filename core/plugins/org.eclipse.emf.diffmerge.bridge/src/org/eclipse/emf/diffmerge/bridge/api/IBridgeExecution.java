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
package org.eclipse.emf.diffmerge.bridge.api;

import org.eclipse.core.runtime.IStatus;


/**
 * The execution of an IBridge.
 * It provides a trace that maps elements in the target data set to the cause of
 * their presence in the source data set.
 * @author Olivier Constant
 */
public interface IBridgeExecution {
  
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
  }
  
}
