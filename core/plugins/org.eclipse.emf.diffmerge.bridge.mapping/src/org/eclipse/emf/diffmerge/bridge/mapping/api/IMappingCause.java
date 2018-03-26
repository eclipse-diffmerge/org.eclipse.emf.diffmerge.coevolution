/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
