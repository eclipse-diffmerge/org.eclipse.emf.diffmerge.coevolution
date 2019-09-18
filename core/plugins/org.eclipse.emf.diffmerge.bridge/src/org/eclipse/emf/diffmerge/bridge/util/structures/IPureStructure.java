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

import java.util.Collection;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A data structure which cannot be empty, maps slots to non-null values,
 * and provides a symbolic representation.
 * @param <E> the type of the elements of the structure
 * @author Olivier Constant
 */
public interface IPureStructure<E> extends Iterable<E>, ISymbolProvider {
  
  /**
   * Return the contents of the structure as a collection.
   * The order of the elements of the collection must be the same for each call of this operation.
   * @return a non-null, non-empty, potentially unmodifiable collection containing no null value
   */
  Collection<E> asCollection();
  
  /**
   * Return the contents of the structure as a collection of (slot, value) couples
   * @return a non-null, non-empty, potentially unmodifiable collection containing no null value
   */
  Collection<? extends Tuple2<?, E>> getContents();
  
  /**
   * Return the dimension of the structure
   * @return a strictly positive int 
   */
  int size();
  
}