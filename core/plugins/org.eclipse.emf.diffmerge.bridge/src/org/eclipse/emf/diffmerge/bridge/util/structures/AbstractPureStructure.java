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
