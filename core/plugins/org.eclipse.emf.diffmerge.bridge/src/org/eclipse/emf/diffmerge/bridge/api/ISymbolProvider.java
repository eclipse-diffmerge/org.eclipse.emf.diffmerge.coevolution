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
package org.eclipse.emf.diffmerge.bridge.api;


/**
 * An object that can provide a symbol that non-ambiguously characterizes it.
 * @author Olivier Constant
 */
public interface ISymbolProvider {
  
  /**
   * Return a symbolic characterization of the receiver based on the
   * given symbol function.
   * Implementations may apply the symbol function to objects
   * that are considered to be a part of the receiver, but not
   * to the receiver itself.
   * @param function_p a potentially null function
   * Invariant:
   *    For every couple of non-null ISymbolProviders (provider1, provider2)
   *    and symbol function f:
   *      provider1.getClass() == provider2.getClass() =>
   *        provider1.getSymbol(f).equals(provider2.getSymbol(f))
   *        ==
   *        provider1.equals(provider2)
   * @return an object which can be null only if function_p is null
   */
  Object getSymbol(ISymbolFunction function_p);
  
}
