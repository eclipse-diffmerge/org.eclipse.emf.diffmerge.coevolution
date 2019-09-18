/*********************************************************************
 * Copyright (c) 2014-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.util.structures;


/**
 * An implementation of 1-Tuples (singletons).
 * @param <E1> the type of the element of the singleton
 * @author Olivier Constant
 */
public class Tuple1<E1> extends TupleN<Object> {
  
  /**
   * Constructor
   * @param e1_p the non-null element of the singleton
   */
  public Tuple1(E1 e1_p) {
    super(e1_p);
  }
  
  /**
   * Technical constructor
   * @param elements_p the non-null, non-empty list of non-null elements of the tuple
   * Duplicates are permitted.
   */
  protected Tuple1(Object... elements_p) {
    super(elements_p);
  }
  
  /**
   * Return the first element of the Tuple
   * @return a non-null object
   */
  public E1 get1() {
    @SuppressWarnings("unchecked") // OK by construction
    E1 result = (E1)get(1);
    return result;
  }
  
}