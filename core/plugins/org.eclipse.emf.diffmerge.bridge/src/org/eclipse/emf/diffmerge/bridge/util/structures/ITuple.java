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
package org.eclipse.emf.diffmerge.bridge.util.structures;

import java.util.List;


/**
 * A definition of Tuples in the mathematical sense, excluding null values.
 * @param <E> the type of the elements of the Tuple
 * @author Olivier Constant
 */
public interface ITuple<E> extends IPureStructure<E> {
  
  /**
   * Return the content of the tuple as a list
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#asCollection()
   */
  List<E> asCollection();
  
  /**
   * Return the element at the given index, starting at 1
   * @param index_p an int which is such that 1 <= index_p <= size()
   * @return a non-null object
   * @throws IndexOutOfBoundsException
   */
  E get(int index_p);
  
}
