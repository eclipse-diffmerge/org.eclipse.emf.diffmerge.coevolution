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

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;


/**
 * The execution of a mapping bridge.
 * @author Olivier Constant
 */
public interface IMappingExecution extends IBridgeExecution {
  
  /**
   * Return the target data element defined by the given rule on the given source data element
   * @param source_p a non-null object
   * @param rule_p a non-null object
   * @return an object which is not null if the rule of the given identifier applies to the given data element
   */
  <S, T> T get(S source_p, IRule<S, T> rule_p);
  
  /**
   * Return the target data element defined by the rule of the given identifier
   * on the given source data element
   * @param source_p a non-null object
   * @param ruleID_p a non-null object
   * @return an object which is not null if the rule of the given identifier applies to the given data element
   */
  <S, T> T get(S source_p, IRuleIdentifier<S, T> ruleID_p);
  
  /**
   * Return all target data elements or structures that are associated to the given source
   * @param source_p a potentially null object
   * @return a non-null, potentially empty, unmodifiable collection
   */
  List<Object> getAll(Object source_p);
  
  /**
   * Return the unique target data element associated to the given source, if indeed unique
   * @param <T> the type of the target (Object if unknown or unsure)
   * @param source_p a non-null object
   * @return a potentially null object
   */
  <T> T getOne(Object source_p);
  
}