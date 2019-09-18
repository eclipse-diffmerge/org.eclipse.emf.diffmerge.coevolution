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

import org.eclipse.emf.diffmerge.bridge.api.ICause.Symbolic;


/**
 * An ICause for IMappingBridge.
 * @param <S> the type of source data elements
 * @param <T> the type of target data elements
 * @author Olivier Constant
 */
public interface IMappingCause<S, T> extends Symbolic<S> {
  
  /**
   * Return the query execution that characterizes this ICause
   * @return a non-null object
   */
  IQueryExecution getQueryExecution();
  
  /**
   * Return the source data element that characterizes this ICause 
   * @return a non-null object
   */
  S getSource();
  
  /**
   * Return the rule that characterizes this ICause
   * @return a non-null object
   */
  IRule<? super S, ?, T> getRule();
  
}
