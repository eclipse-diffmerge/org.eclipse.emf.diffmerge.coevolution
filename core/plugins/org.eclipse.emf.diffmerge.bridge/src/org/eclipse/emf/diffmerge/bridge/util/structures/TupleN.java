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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;


/**
 * A Tuple whose dimension is not constrained to be defined statically.
 * It can be seen as a non-modifiable List whose elements cannot be null.
 * @param <E> the type of the elements of the Tuple
 * @author Olivier Constant
 */
public class TupleN<E> extends AbstractPureStructure<E> implements ITuple<E> {
  
  /** The non-null, non-modifiable list of elements of the Tuple */
  protected final List<E> _elements;
  
  
  /**
   * Constructor
   * @param elements_p the non-null, non-empty list of non-null elements of the tuple
   * Duplicates are permitted.
   */
  public TupleN(E... elements_p) {
    _elements = Arrays.asList(elements_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#asCollection()
   */
  public List<E> asCollection() {
    return _elements;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    boolean result = false;
    if (object_p instanceof ITuple<?>) {
      ITuple<?> peer = (ITuple<?>)object_p;
      result = asCollection().equals(peer.asCollection());
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.ITuple#get(int)
   */
  public E get(int index_p) {
    return _elements.get(index_p-1);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#getContents()
   */
  public Collection<Tuple2<Integer, E>> getContents() {
    List<Tuple2<Integer, E>> result =
        new ArrayList<Tuple2<Integer, E>>(_elements.size());
    int i = 1;
    for (E element : _elements) {
      result.add(Tuples.tuple(Integer.valueOf(i), element));
      i++;
    }
    return Collections.unmodifiableCollection(result);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    StringBuilder builder = new StringBuilder();
    builder.append('(');
    boolean first = true;
    for (E element : _elements) {
      if (first) {
        first = false;
      } else {
        builder.append(',');
        builder.append(' ');
      }
      Object elementSymbol = function_p.getSymbol(element);
      builder.append(elementSymbol); // Might be null if function_p is not appropriate
    }
    builder.append(')');
    return builder.toString();
  }
  
  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return _elements.hashCode();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.AbstractPureStructure#size()
   */
  @Override
  public int size() {
    return _elements.size();
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + asCollection().toString();
  }
  
}
