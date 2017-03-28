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
package org.eclipse.emf.diffmerge.bridge.api;

import java.util.Collection;


/**
 * The cause of the presence of target data elements in terms of source data.
 * @param <S> the type of source data elements
 * @param <T> the type of target data elements
 * @author Olivier Constant
 */
public interface ICause<S, T> {
  
  /**
   * Return the source data elements involved in this cause
   * @return a non-null, unmodifiable ordered set
   */
  Collection<S> getSourceElements();
  
  
  /**
   * A cause that can be represented as a symbol.
   * @param <S> the type of source data elements
   * @param <T> the type of target data elements
   */
  interface Symbolic<S, T> extends ICause<S, T>, ISymbolProvider {
    // Nothing needed
  }
  
}
