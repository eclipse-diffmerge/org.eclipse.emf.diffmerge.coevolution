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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;


/**
 * An IRuleIdentifier for rules where the whole source data traces the target data.
 * @param <S> the type of the source data of the rule
 * @param <T> the type of the target data of the rule
 * @author Olivier Constant
 */
public class RuleIdentifier<S, T> extends CommonRuleIdentifier<S, S, T> {
  
  /**
   * Default constructor
   */
  public RuleIdentifier() {
    super();
  }
  
  /**
   * Constructor
   * @param name_p a non-null name which must be unique within a IMappingBridge
   */
  public RuleIdentifier(String name_p) {
    super(name_p);
  }
  
}
