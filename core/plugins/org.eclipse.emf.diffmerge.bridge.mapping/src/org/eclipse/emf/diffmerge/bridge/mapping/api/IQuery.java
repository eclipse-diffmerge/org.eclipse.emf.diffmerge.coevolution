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
package org.eclipse.emf.diffmerge.bridge.mapping.api;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.diffmerge.bridge.api.IIdentifiedWithType;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A query.
 * A query takes existing data as input and returns existing data as output.
 * A query has a unique identifier and is characterized by the type of its
 * input and output.
 * Other queries can depend on its output; those are the sub-queries.
 * Query interdependencies are structured as forests.
 * A query may also have transformation rules that are fed by the output
 * of the query.
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author Olivier Constant
 */
public interface IQuery<I, O> extends IQueryHolder<O>, IDataFunction<I, O>,
IIdentifiedWithType<IQueryIdentifier<O>>, ISymbolProvider {
  
  /**
   * Accept the given rule as a data consumer
   * @param rule_p a non-null rule
   */
  void accept(IRule<? super O, ?> rule_p);
  
  /**
   * Return an iterator over the results of the query on the given source data
   * in the given evaluation environment.
   * The iterator is not allowed to yield null values.
   * @param environment_p a non-null environment
   * @return a non-null iterator
   */
  Iterator<O> evaluate(I input_p, IQueryExecution environment_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IDataConsumer#getInputProvider()
   */
  IQueryHolder<? extends I> getInputProvider();
  
  /**
   * Return the rules which act as data consumers
   * @return a non-null, possibly empty ordered set
   */
  Collection<? extends IRule<? super O, ?>> getRules();
  
}
