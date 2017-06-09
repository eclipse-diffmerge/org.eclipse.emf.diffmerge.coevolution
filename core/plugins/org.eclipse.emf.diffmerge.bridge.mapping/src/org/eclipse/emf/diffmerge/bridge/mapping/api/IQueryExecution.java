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

import java.util.List;


/**
 * An execution context for queries.
 * It associates query identifiers to results of the corresponding queries.
 * Query identifiers and results are totally ordered. This order reflects the
 * query/sub-query hierarchy, i.e., the list of query identifiers represents a path
 * in the forest of queries.
 * @author Olivier Constant
 */
public interface IQueryExecution {
  
  /**
   * Return the result of the given query
   * @param query_p a non-null object
   * @return an object which is not null if this environment encompasses the given query
   */
  <O> O get(IQuery<?, O> query_p);
  
  /**
   * Return the result of the query of the given identifier
   * @param queryID_p a non-null object
   * @return an object which is not null if this environment encompasses the query of the given identifier
   */
  <O> O get(IQueryIdentifier<O> queryID_p);
  
  /**
   * Return the ordered set of query results in the same order as query identifiers
   * Invariant: getAll().size() == getQueryIdentifiers().size()
   * @return a non-null, possibly empty list
   */
  List<Object> getAll();
  
  /**
   * Return the result of the last query, if any
   * Invariant: getAll().isEmpty() && getLast() == null ||
   *   !getAll().isEmpty() && getAll().get(getAll().size()-1) == getLast()
   * @return a non-null object
   */
  Object getLast();
  
  /**
   * Return the ordered set of query identifiers, from super-queries to sub-queries
   * @return a non-null, possibly empty list
   */
  List<? extends IQueryIdentifier<?>> getQueryIdentifiers();
  
}
