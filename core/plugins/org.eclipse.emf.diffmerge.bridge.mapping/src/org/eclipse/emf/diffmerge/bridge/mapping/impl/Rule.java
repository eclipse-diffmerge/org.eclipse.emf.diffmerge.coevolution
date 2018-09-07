/*********************************************************************
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;


/**
 * A base implementation of IRule where the whole source data traces the target data.
 * @param <S> the type of source data
 * @param <T> the type of target data
 * @see IRule
 * @author Olivier Constant
 */
public abstract class Rule<S, T>
extends CommonRule<S, S, T> {
  
  /**
   * Default constructor for a randomly generated ID
   * @param provider_p a non-null input provider
   */
  public Rule(IQuery<?, ? extends S> provider_p) {
    super(provider_p, new RuleIdentifier<S, T>());
  }
  
  /**
   * Constructor
   * @param provider_p a non-null input provider
   * @param id_p the non-null identifier of the rule
   */
  public Rule(IQuery<?, ? extends S> provider_p, String id_p) {
    super(provider_p, id_p);
  }
  
  /**
   * Constructor
   * @param provider_p a non-null input provider
   * @param id_p the non-null identifier of the rule
   */
  public Rule(IQuery<?, ? extends S> provider_p, RuleIdentifier<S, T> id_p) {
    super(provider_p, id_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#traceSource(java.lang.Object)
   */
  public S traceSource(S source_p) {
    return source_p;
  }
  
}
