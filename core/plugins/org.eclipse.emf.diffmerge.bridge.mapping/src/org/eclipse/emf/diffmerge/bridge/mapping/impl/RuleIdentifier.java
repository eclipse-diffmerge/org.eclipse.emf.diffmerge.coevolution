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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import java.util.UUID;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.impl.AbstractNamedElement;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRuleIdentifier;


/**
 * A simple implementation of IRuleIdentifier.
 * The name of a RuleIdentifier is assumed to be unique in every IMappingBridge that uses it.
 * @param <S> the type of the source data of the rule
 * @param <T> the type of the target data of the rule
 * @author Olivier Constant
 */
public class RuleIdentifier<S, T> extends AbstractNamedElement
implements IRuleIdentifier<S, T> {
  
  /**
   * Default constructor
   */
  public RuleIdentifier() {
    this(UUID.randomUUID().toString());
  }
  
  /**
   * Constructor
   * @param name_p a non-null name which must be unique within a IMappingBridge
   */
  public RuleIdentifier(String name_p) {
    super(name_p);
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    if (!(object_p instanceof IRuleIdentifier<?, ?>))
      return false;
    return getSymbol(null).equals(
        ((IRuleIdentifier<?, ?>)object_p).getSymbol(null));
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
