/*********************************************************************
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
 * A function that can convert objects to symbols that non-ambiguously characterize them.
 * @see ISymbolProvider
 * @author Olivier Constant
 */
public interface ISymbolFunction {
  
  /**
   * Return a symbolic characterization of the given object.
   * This operation must be idempotent for a given symbol function:
   * successive calls with the same parameter must yield the same results.
   * The characterization must be non-ambiguous in the sense that two different objects
   * must not have the same symbol if they belong to the same data set.
   * Invariant:
   *    For every couple of non-null objects (o1, o2) from the same usage context:
   *      (o1.getClass() == o2.getClass() &&
   *        getSymbol(o1) != null &&  getSymbol(o2) != null)
   *      IMPLIES
   *      getSymbol(o1).equals(getSymbol(o2)) == o1.equals(o2)
   * @param object_p a non-null object
   * @return a potentially null object
   */
  Object getSymbol(Object object_p);
  
}