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

import java.util.Collection;


/**
 * A data provider that feeds queries.
 * @param <I> the type of the query inputs
 * @author Olivier Constant
 */
public interface IQueryHolder<I> extends IDataProvider<I> {
  
  /**
   * Accept the given query as a data consumer
   * @param query_p a non-null query
   */
  void accept(IQuery<? super I, ?> query_p);
  
  /**
   * Return the queries which act as data consumers
   * @return a non-null, possibly empty ordered set
   */
  Collection<? extends IQuery<? super I, ?>> getQueries();
  
}
