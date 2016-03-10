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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
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
  
}
