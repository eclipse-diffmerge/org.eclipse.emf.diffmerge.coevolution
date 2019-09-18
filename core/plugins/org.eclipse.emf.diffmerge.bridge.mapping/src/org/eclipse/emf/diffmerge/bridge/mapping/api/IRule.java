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
package org.eclipse.emf.diffmerge.bridge.mapping.api;

import org.eclipse.emf.diffmerge.bridge.api.IIdentifiedWithType;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A transformation rule.
 * A transformation rule takes existing data as input and returns expected
 * new data as output, yielding a trace between a subset of the input data and
 * the output data.
 * The input data is provided by a query.
 * A rule also has a unique persistable identifier.
 * @param <S> the type of source data elements
 * @param <TRS> the type of the source subset that traces the target
 * @param <T> the type of target data elements
 * @author Olivier Constant
 */
public interface IRule<S, TRS, T> extends IIdentifiedWithType<IRuleIdentifier<S, TRS, T>>,
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
   * Return the subset of data in source_p that allows tracing the target data element
   * @param source_p a non-null object
   * @return a non-null object which can be source_p
   */
  TRS traceSource(S source_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IDataConsumer#getInputProvider()
   */
  IQuery<?, ? extends S> getInputProvider();
  
}
