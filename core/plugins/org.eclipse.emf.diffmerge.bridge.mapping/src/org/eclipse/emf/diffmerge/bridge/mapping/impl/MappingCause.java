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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import static org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil.flatten;

import java.util.Collection;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingCause;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;


/**
 * An implementation of IMappingCause.
 * @see IMappingCause
 * @param <S> the type of source data elements
 * @param <T> the type of target data elements
 * @author Olivier Constant
 */
public class MappingCause<S, T> implements IMappingCause<S, T> {
  
  /** The optional query execution */
  private final IQueryExecution _queryExecution;
  
  /** The non-null source */
  private final S _source;
  
  /** The non-null rule */
  private final IRule<? super S, ?, T> _rule;
  
  
  /**
   * Constructor
   * @param queryExecution_p an optional query execution
   * @param source_p a non-null source
   * @param rule_p a non-null rule
   */
  public MappingCause(IQueryExecution queryExecution_p, S source_p, IRule<? super S, ?, T> rule_p) {
    _queryExecution = queryExecution_p;
    _source = source_p;
    _rule = rule_p;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object other_p) {
    boolean result = false;
    if (other_p instanceof MappingCause) {
      MappingCause<?,?> peer = (MappingCause<?,?>)other_p;
      IQueryExecution qex = getQueryExecution();
      result =
        (qex == null || qex.equals(peer.getQueryExecution())) &&
        getSource().equals(peer.getSource()) &&
        getRule().equals(peer.getRule());
    }
    return result; 
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingCause#getQueryExecution()
   */
  public IQueryExecution getQueryExecution() {
    return _queryExecution;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingCause#getRule()
   */
  public IRule<? super S, ?, T> getRule() {
    return _rule;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingCause#getSource()
   */
  public S getSource() {
    return _source;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ICause#getSourceElements()
   */
  public Collection<?> getSourceElements() {
    Collection<?> result = flatten(_source);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    String result = null;
    // Source
    Object sourceIdentification = function_p.getSymbol(_source);
    if (sourceIdentification != null) {
      // Rule
      Object ruleIdentification = function_p.getSymbol(_rule);
      if (ruleIdentification != null) {
        // Union
        StringBuilder builder = new StringBuilder();
        builder.append("Source["); //$NON-NLS-1$
        builder.append(sourceIdentification);
        builder.append("]_Rule["); //$NON-NLS-1$
        builder.append(ruleIdentification);
        builder.append(']');
        result = builder.toString();
      }
    }
    return result;
  }
  
  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    IQueryExecution qex = getQueryExecution();
    int result = 1;
    result = prime * result + (qex == null? 0: qex.hashCode());
    result = prime * result + getSource().hashCode();
    result = prime * result + getRule().hashCode();
    return result;
  }  
}
