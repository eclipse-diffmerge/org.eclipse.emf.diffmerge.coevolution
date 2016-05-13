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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;
import org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil;


/**
 * A base implementation of IQuery.
 * @param <I> the type of the input data
 * @param <O> the type of the output data
 * @see IQuery
 * @author Olivier Constant
 */
public abstract class Query<I, O> extends AbstractQueryHolder<O>
implements IQuery<I, O> {
  
  /** The non-null identifier of the query */
  private final IQueryIdentifier<O> _identifier;
  
  /** The non-null holder */
  private final IQueryHolder<? extends I> _holder;
  
  /** The non-null ordered set of rules */
  private final Set<IRule<? super O, ?>> _rules;
  
  
  /**
   * Default constructor
   * @param parent_p a non-null holder and data provider
   */
  public Query(IQueryHolder<? extends I> parent_p) {
    this(parent_p, (QueryIdentifier<O>)null);
  }
  
  /**
   * Constructor
   * @param parent_p a non-null holder and data provider
   * @param id_p the non-null identifier of the query
   */
  public Query(IQueryHolder<? extends I> parent_p, String id_p) {
    this(parent_p, new QueryIdentifier<O>(id_p));
  }
  
  /**
   * Constructor
   * @param parent_p a non-null holder and data provider
   * @param id_p the optional identifier of the query
   */
  public Query(IQueryHolder<? extends I> parent_p, IQueryIdentifier<O> id_p) {
    _holder = parent_p;
    parent_p.accept(this);
    _identifier = id_p != null? id_p: getDefaultID();
    _rules = new LinkedHashSet<IRule<? super O, ?>>();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#accept(org.eclipse.emf.diffmerge.bridge.mapping.api.IRule)
   */
  public void accept(IRule<? super O, ?> rule_p) {
    _rules.add(rule_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#getInputProvider()
   */
  public final IQueryHolder<? extends I> getInputProvider() {
    return _holder;
  }
  
  /**
   * Return a default ID for the query
   * @return a non-null ID
   */
  protected IQueryIdentifier<O> getDefaultID() {
    return new QueryIdentifier<O>(getClass().getSimpleName());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IIdentifiedWithType#getID()
   */
  public IQueryIdentifier<O> getID() {
    return _identifier;
  }
  
  /**
   * Return an iterator over the given array of output elements
   * @param elements_p a possibly empty, possibly null array
   * @return a non-null iterator
   */
  protected Iterator<O> getIterator(O... elements_p) {
    if (elements_p == null || elements_p.length == 0 ||
        elements_p.length == 1 && elements_p[0] == null)
      return CollectionsUtil.emptyIterator();
    return Arrays.asList(elements_p).iterator();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#getRules()
   */
  public Collection<IRule<? super O, ?>> getRules() {
    return Collections.unmodifiableCollection(_rules);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    return function_p.getSymbol(getID());
  }
  
}
