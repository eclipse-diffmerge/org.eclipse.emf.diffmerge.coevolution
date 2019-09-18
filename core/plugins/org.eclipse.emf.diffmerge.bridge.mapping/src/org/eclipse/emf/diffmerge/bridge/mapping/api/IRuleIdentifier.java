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

import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * An identifier for rules.
 * @param <S> the type of the source data of the rule
 * @param <TRS> the type of the source subset that traces the target
 * @param <T> the type of the target data of the rule
 * @author Olivier Constant
 */
public interface IRuleIdentifier<S, TRS, T> extends ISymbolProvider {
  
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
