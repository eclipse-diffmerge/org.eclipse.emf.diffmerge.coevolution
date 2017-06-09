/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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