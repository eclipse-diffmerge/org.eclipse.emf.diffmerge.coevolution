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
