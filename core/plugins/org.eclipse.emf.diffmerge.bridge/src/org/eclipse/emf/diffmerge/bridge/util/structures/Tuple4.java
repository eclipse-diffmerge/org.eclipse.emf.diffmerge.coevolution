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
package org.eclipse.emf.diffmerge.bridge.util.structures;


/**
 * An implementation of 4-Tuples.
 * @param <E1> the type of the first element of the Tuple
 * @param <E2> the type of the second element of the Tuple
 * @param <E3> the type of the third element of the Tuple
 * @param <E4> the type of the fourth element of the Tuple
 * @author Olivier Constant
 */
public class Tuple4<E1, E2, E3, E4> extends Tuple3<E1, E2, E3> {
  
  /**
   * Constructor
   * @param e1_p the non-null first element of the Tuple
   * @param e2_p the non-null second element of the Tuple
   * @param e3_p the non-null third element of the Tuple
   * @param e4_p the non-null fourth element of the Tuple
   */
  public Tuple4(E1 e1_p, E2 e2_p, E3 e3_p, E4 e4_p) {
    super(e1_p, e2_p, e3_p, e4_p);
  }
  
  /**
   * Technical constructor
   * @param elements_p the non-null, non-empty list of non-null elements of the tuple
   * Duplicates are permitted.
   */
  protected Tuple4(Object... elements_p) {
    super(elements_p);
  }
  
  /**
   * Return the fourth element of the Tuple
   * @return a non-null object
   */
  public E4 get4() {
    @SuppressWarnings("unchecked") // OK by construction
    E4 result = (E4)get(4);
    return result;
  }
  
}
