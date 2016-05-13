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

import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;


/**
 * A base implementation of IQuery and IRule, all in one.
 * @param <S> the type of the source data elements
 * @param <X> the type of the source data elements that trigger the presence of target data elements
 * @param <T> the type of the target data elements
 * @see Query
 * @see Rule
 * @author Olivier Constant
 */
public abstract class QueryAndRule<S, X, T> extends Query<S, X> {
  
  /**
   * Identifiers for query-and-rules.
   * @param <X> the type of the output of the query
   * @param <T> the type of the output of the rule
   */
  public static class QueryAndRuleIdentifier<X, T> extends RuleIdentifier<X, T>
  implements IQueryIdentifier<X> {
    /**
     * Constructor
     * @param name_p a non-null name which must be unique within a IMappingBridge
     */
    public QueryAndRuleIdentifier(String name_p) {
      super(name_p);
    }
  }
  
  /**
   * A rule embedded in a query-and-rule.
 * @param <S> the type of source data
 * @param <T> the type of target data
   */
  protected static class InnerRule<S, T> extends Rule<S, T> {
    /**
     * Constructor
     * @param queryAndRule_p a non-null query-and-rule
     */
    public InnerRule(QueryAndRule<?, S, T> queryAndRule_p) {
      super(queryAndRule_p, queryAndRule_p.getID());
    }
    /**
     * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
     */
    public T createTarget(S source_p, IQueryExecution queryExecution_p) {
      return getInputProvider().createTarget(source_p, queryExecution_p);
    }
    /**
     * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
     */
    public void defineTarget(S source_p, T target_p,
        IQueryExecution queryExecution_p, IMappingExecution mappingExecution_p) {
      getInputProvider().defineTarget(source_p, target_p, queryExecution_p, mappingExecution_p);
    }
    /**
     * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.Rule#getInputProvider()
     */
    @Override
    @SuppressWarnings("unchecked")
    public QueryAndRule<?, S, T> getInputProvider() {
      return (QueryAndRule<?, S, T>)super.getInputProvider();
    }
  }
  
  
  /** The non-null main rule */
  private final InnerRule<X, T> _mainRule;
  
  
  /**
   * Default constructor
   * @param parent_p a non-null holder and data provider
   */
  public QueryAndRule(IQueryHolder<? extends S> parent_p) {
    this(parent_p, (QueryAndRuleIdentifier<X, T>)null);
  }
  
  /**
   * Constructor
   * @param parent_p a non-null holder and data provider
   * @param id_p the non-null identifier of the query
   */
  public QueryAndRule(IQueryHolder<? extends S> parent_p, String id_p) {
    this(parent_p, new QueryAndRuleIdentifier<X, T>(id_p));
  }
  
  /**
   * Constructor
   * @param parent_p a non-null holder and data provider
   * @param id_p the optional identifier of the query
   */
  public QueryAndRule(IQueryHolder<? extends S> parent_p, QueryAndRuleIdentifier<X, T> id_p) {
    super(parent_p, id_p);
    _mainRule = new InnerRule<X, T>(this);
  }
  
  /**
   * @see IRule#createTarget(Object, IQueryExecution)
   */
  public abstract T createTarget(X source_p, IQueryExecution queryExecution_p);
  
  /**
   * @see IRule#defineTarget(Object, Object, IQueryExecution, IMappingExecution)
   */
  public abstract void defineTarget(X source_p, T target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.Query#getDefaultID()
   */
  @Override
  protected QueryAndRuleIdentifier<X, T> getDefaultID() {
    return new QueryAndRuleIdentifier<X, T>(getClass().getSimpleName());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.Query#getID()
   */
  @Override
  @SuppressWarnings("unchecked")
  public final QueryAndRuleIdentifier<X, T> getID() {
    return (QueryAndRuleIdentifier<X, T>)super.getID();
  }
  
  /**
   * Return the main rule
   * @return a non-null object
   */
  public IRule<X, T> getMainRule() {
    return _mainRule;
  }
  
}
