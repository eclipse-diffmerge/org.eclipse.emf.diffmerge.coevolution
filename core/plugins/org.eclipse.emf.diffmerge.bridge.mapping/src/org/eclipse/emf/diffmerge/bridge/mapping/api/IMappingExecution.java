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
package org.eclipse.emf.diffmerge.bridge.mapping.api;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.api.INavigableBridgeExecution;


/**
 * The execution of a mapping bridge.
 * @author Olivier Constant
 */
public interface IMappingExecution extends INavigableBridgeExecution {
  
  /**
   * Return the target data element or structure defined by the given rule on the given
   * source data element
   * @param source_p a non-null object
   * @param rule_p a non-null object
   * @return an object which is not null if the rule of the given identifier applies to the given data element
   */
  <S, T> T get(S source_p, IRule<S, T> rule_p);
  
  /**
   * Return the target data element or structure defined by the rule of the given identifier
   * on the given source data element
   * @param source_p a non-null object
   * @param ruleID_p a non-null object
   * @return an object which is not null if the rule of the given identifier applies to the given data element
   */
  <S, T> T get(S source_p, IRuleIdentifier<S, T> ruleID_p);
  
  /**
   * Return all target data elements or structures that are associated to the given source.
   * Structures are not flattened in the resulting list, contrary to getAll(Object, Class).
   * @param source_p a potentially null object
   * @return a non-null, potentially empty, unmodifiable collection
   */
  List<Object> getAll(Object source_p);
  
  /**
   * Return all target data elements of the given type that are associated to the
   * given source
   * @param source_p a potentially null object
   * @param type_p a non-null type
   * @param <T> the type of the expected result
   * @return a non-null, potentially empty, unmodifiable collection
   */
  <T> List<T> getAll(Object source_p, Class<T> type_p);
  
  /**
   * Return a target data element of the given type that are associated to the given
   * source, if any. If more than one such element exist, then which one is returned
   * is undefined.
   * @param source_p a non-null object
   * @param type_p a non-null type
   * @param <T> the type of the expected result
   * @return a potentially null object
   */
  <T> T getOne(Object source_p, Class<T> type_p);
  
  /**
   * Return the source data elements that are inputs of the given rule, restricted to the given
   * query execution context if any
   * @param <S> the type of the source data elements
   * @param rule_p a non-null rule
   * @param context_p an optional query execution
   * @return a non-null iterable
   */
  <S> Collection<S> getRuleInputs(IRule<S,?> rule_p, IQueryExecution context_p);
  
  /**
   * Return the source data elements that are inputs of rules of the given identifier,
   * restricted to (non-strict) sub-executions of the given query execution if any
   * @param <S> the type of the source data elements
   * @param ruleID_p a non-null rule ID
   * @param context_p an optional query execution
   * @return a non-null iterable
   */
  <S> Collection<S> getRuleInputs(IRuleIdentifier<S,?> ruleID_p, IQueryExecution context_p);
  
  /**
   * Return the target scope from the mapping execution. It may only be used for
   * storing the target elements in the data set when applicable.
   * Beware that only the constant, fixed part of the data set may be used for that purpose,
   * not the variable part that is defined by rules.
   * @param <TD> the type of the target scope
   * @return the (non-null) target scope
   */
  <TD> TD getTargetDataSet();
  
  
  /**
   * Modifiable extension of IMappingExecution.
   */
  interface Editable extends IMappingExecution, INavigableBridgeExecution.Editable {
	
	  /**
	   * Register the given target data elements with the given cause
	   * in the execution trace, if any
	   * @param cause_p a non-null cause
	   * @param target_p a non-null target data element
	   */
	  void putInTrace(ICause<?> cause_p, Object target_p) ;
  }
  
}