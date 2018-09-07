/*********************************************************************
 * Copyright (c) 2017-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.api;


/**
 * The execution of an IBridge that provides the ability to navigate from
 * source data to corresponding target data via explicit causes of the
 * presence of target data elements expressed with source data elements.
 * @author Olivier Constant
 */
public interface INavigableBridgeExecution extends IBridgeExecution {
  
  /**
   * Return the target data element that is associated to the given cause
   * @param cause_p a non-null cause
   * @return an object which is not null if the cause is applicable
   */
  Object get(ICause<?> cause_p);
  
  
  /**
   * Modifiable extension of IBridgeExecution.
   */
  interface Editable extends IBridgeExecution.Editable {
    /**
     * Associate and register the given target data with the given cause
     * @param cause_p a non-null cause
     * @param target_p a non-null target data element
     */
    void put(ICause<?> cause_p, Object target_p);
  }
  
}
