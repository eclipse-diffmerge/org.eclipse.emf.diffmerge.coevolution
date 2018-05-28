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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;


/**
 * A simple implementation of IQueryHolder.
 * @param <I> the type of the query inputs
 * @see IQueryHolder
 * @author Olivier Constant
 */
public abstract class AbstractQueryHolder<I> implements IQueryHolder<I> {
  
  /** The non-null ordered set of queries */
  private final Set<IQuery<? super I, ?>> _queries;
  
  
  /**
   * Constructor
   */
  public AbstractQueryHolder() {
    _queries = new LinkedHashSet<IQuery<? super I, ?>>();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder#accept(org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery)
   */
  public void accept(IQuery<? super I, ?> query_p) {
    _queries.add(query_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder#getQueries()
   */
  public Collection<? extends IQuery<? super I, ?>> getQueries() {
    return Collections.unmodifiableCollection(_queries);
  }
  
  /**
   * Return a new iterable over elements of the given type
   * @param <TYPE> the type of the elements
   * @return a non-null, modifiable, empty list
   */
  protected <TYPE> List<TYPE> newIterable() {
    return new LinkedList<TYPE>();
  }
  
  /**
   * Return an iterable over the given elements
   * @param elements_p a possibly empty, possibly null array or sequence of parameters
   * @return a non-null, non-modifiable list
   */
  protected <TYPE> List<TYPE> newIterable(TYPE... elements_p) {
    if (elements_p == null || elements_p.length == 0 ||
        elements_p.length == 1 && elements_p[0] == null)
      return Collections.emptyList();
    return Arrays.asList(elements_p);
  }
  
}
