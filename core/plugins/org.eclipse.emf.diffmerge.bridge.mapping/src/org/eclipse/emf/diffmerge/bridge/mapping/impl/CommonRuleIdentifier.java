/*********************************************************************
 * Copyright (c) 2018 Thales Global Services S.A.S.
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

import java.util.UUID;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.impl.AbstractNamedElement;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRuleIdentifier;


/**
 * A simple implementation of IRuleIdentifier.
 * The name of a CommonRuleIdentifier is assumed to be unique in every IMappingBridge that uses it.
 * @param <S> the type of the source data of the rule
 * @param <TRS> the type of the source subset that traces the target
 * @param <T> the type of the target data of the rule
 * @author Olivier Constant
 */
public class CommonRuleIdentifier<S, TRS, T> extends AbstractNamedElement
implements IRuleIdentifier<S, TRS, T> {
  
  /**
   * Default constructor
   */
  public CommonRuleIdentifier() {
    this(UUID.randomUUID().toString());
  }
  
  /**
   * Constructor
   * @param name_p a non-null name which must be unique within a IMappingBridge
   */
  public CommonRuleIdentifier(String name_p) {
    super(name_p);
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    if (!(object_p instanceof IRuleIdentifier<?, ?, ?>))
      return false;
    return getSymbol(null).equals(
        ((IRuleIdentifier<?, ?, ?>)object_p).getSymbol(null));
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public String getSymbol(ISymbolFunction function_p) {
    return getName(); // Hence the uniqueness constraint
  }
  
  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return getSymbol(null).hashCode();
  }
  
}
