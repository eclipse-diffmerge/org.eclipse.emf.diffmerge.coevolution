/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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
 * The explicit identification of atomic source elements allows for better support
 * for basic features such as logging.
 * @param <S> the type of source data elements
 * @author Olivier Constant
 */
public interface ICause<S> {
  
  /**
   * Return the atomic source data elements involved in this cause
   * @return a non-null, unmodifiable ordered set
   */
  Collection<?> getSourceElements();
  
  
  /**
   * A cause that can be represented as a symbol.
   * @param <S> the type of source data elements
   */
  interface Symbolic<S> extends ICause<S>, ISymbolProvider {
    // Nothing needed
  }
  
}
