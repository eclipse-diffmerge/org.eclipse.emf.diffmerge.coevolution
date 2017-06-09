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

import org.eclipse.emf.diffmerge.bridge.api.IIdentifiedWithType;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A transformation rule.
 * A transformation rule takes existing data as input and returns expected
 * data as output.
 * The input data is provided by a query.
 * That definition is described according to the data returned by the query,
 * which plays the role of source for the rule.
 * A rule also has a unique persistable identifier.
 * @param <S> the type of source data elements
 * @param <T> the type of target data elements
 * @author Olivier Constant
 */
public interface IRule<S, T> extends IIdentifiedWithType<IRuleIdentifier<S, T>>,
IDataFunction<S, T>, ISymbolProvider {
  
  /**
   * Create and return an empty target data element for the given source data element
   * and query execution. The result must be usable as-is for the definition of other
   * target data elements (see defineTarget).
   * If a symbol-based trace is being used, the result must be identifiable as-is via
   * the symbol function.
   * The source data element and the query execution are not modified by the operation.
   * @param source_p a non-null object
   * @param queryExecution_p a non-null object
   * @return a non-null object
   */
  T createTarget(S source_p, IQueryExecution queryExecution_p);
  
  /**
   * Define the given target data element according to the given source data element
   * and the given query and rule executions in the context of the given target data set.
   * The source data element and the executions are not modified by the operation.
   * @param source_p a non-null object
   * @param target_p a non-null object
   * @param queryExecution_p a non-null object
   * @param mappingExecution_p a non-null object
   */
  void defineTarget(S source_p, T target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IDataConsumer#getInputProvider()
   */
  IQuery<?, ? extends S> getInputProvider();
  
}
