/**
 * <copyright>
 * 
 * Copyright (c) 2014-2016 Thales Global Services S.A.S.
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
