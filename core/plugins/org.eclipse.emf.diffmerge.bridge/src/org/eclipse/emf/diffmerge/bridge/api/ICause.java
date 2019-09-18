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
