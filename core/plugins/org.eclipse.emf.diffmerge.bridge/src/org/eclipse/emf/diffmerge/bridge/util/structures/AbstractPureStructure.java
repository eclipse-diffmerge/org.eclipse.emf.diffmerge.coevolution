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
package org.eclipse.emf.diffmerge.bridge.util.structures;

import java.util.Iterator;


/**
 * A base implementation for IPureStructure.
 * @param <E> the type of the elements
 * @author Olivier Constant
 */
public abstract class AbstractPureStructure<E> implements IPureStructure<E> {
  
  /**
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<E> iterator() {
    return asCollection().iterator();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#size()
   */
  public int size() {
    return asCollection().size();
  }
  
}
