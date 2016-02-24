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
package org.eclipse.emf.diffmerge.bridge.util.structures;


/**
 * A utility class for Tuples.
 * @author Olivier Constant
 */
public final class Tuples {
  
  /**
   * Constructor
   */
  private Tuples() {
    // Forbids instantiation
  }
  
  /**
   * Return a Tuple made of the given element
   * @param e1_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1> Tuple1<E1> tuple(E1 e1_p) {
    return new Tuple1<E1>(e1_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param e1_p a non-null object
   * @param e2_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1, E2> Tuple2<E1, E2> tuple(E1 e1_p, E2 e2_p) {
    return new Tuple2<E1, E2>(e1_p, e2_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param e1_p a non-null object
   * @param e2_p a non-null object
   * @param e3_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1, E2, E3> Tuple3<E1, E2, E3> tuple(E1 e1_p, E2 e2_p, E3 e3_p) {
    return new Tuple3<E1, E2, E3>(e1_p, e2_p, e3_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param e1_p a non-null object
   * @param e2_p a non-null object
   * @param e3_p a non-null object
   * @param e4_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> tuple(
      E1 e1_p, E2 e2_p, E3 e3_p, E4 e4_p) {
    return new Tuple4<E1, E2, E3, E4>(e1_p, e2_p, e3_p, e4_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param e1_p a non-null object
   * @param e2_p a non-null object
   * @param e3_p a non-null object
   * @param e4_p a non-null object
   * @param e5_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1, E2, E3, E4, E5> Tuple5<E1, E2, E3, E4, E5> tuple(
      E1 e1_p, E2 e2_p, E3 e3_p, E4 e4_p, E5 e5_p) {
    return new Tuple5<E1, E2, E3, E4, E5>(e1_p, e2_p, e3_p, e4_p, e5_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param e1_p a non-null object
   * @param e2_p a non-null object
   * @param e3_p a non-null object
   * @param e4_p a non-null object
   * @param e5_p a non-null object
   * @param e6_p a non-null object
   * @return a non-null Tuple
   */
  public static <E1, E2, E3, E4, E5, E6> Tuple6<E1, E2, E3, E4, E5, E6> tuple(
      E1 e1_p, E2 e2_p, E3 e3_p, E4 e4_p, E5 e5_p, E6 e6_p) {
    return new Tuple6<E1, E2, E3, E4, E5, E6>(e1_p, e2_p, e3_p, e4_p, e5_p, e6_p);
  }
  
  /**
   * Return a Tuple made of the given elements
   * @param elements_p a non-empty array of non-null objects
   * @return a non-null Tuple
   */
  public static <E> ITuple<E> tupleN(E... elements_p) {
    return new TupleN<E>(elements_p);
  }
  
}
