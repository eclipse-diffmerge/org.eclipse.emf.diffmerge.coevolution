/**
 * <copyright>
 * 
 * Copyright (c) 2014-2016 Thales Global Services S.A.S.
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

import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * An identifier for rules.
 * @param <S> the type of the source data of the rule
 * @param <T> the type of the target data of the rule
 * @author Olivier Constant
 */
public interface IRuleIdentifier<S, T> extends ISymbolProvider {
  
  /**
   * Must be redefined.
   * Two rule identifiers are equal if and only if their persistable representations are equal.
   * @see Object#equals(Object)
   * @param object_p a possibly null object
   * @return whether equality holds
   */
  boolean equals(Object object_p);
  
  /**
   * Must be redefined together with equals(Object)
   * @see Object#hashCode()
   */
  int hashCode();
  
}
