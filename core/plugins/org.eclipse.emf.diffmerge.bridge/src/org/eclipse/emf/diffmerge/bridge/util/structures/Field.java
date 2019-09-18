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

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.impl.AbstractNamedElement;


/**
 * A base implementation of IField.
 * @author Olivier Constant
 */
public class Field<T> extends AbstractNamedElement implements IField<T>, Comparable<Field<?>> {
  
  /**
   * Constructor
   * @param name_p the non-null name
   */
  public Field(String name_p) {
    super(name_p);
  }
  
  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Field<?> o_p) {
    return getSymbol(null).compareTo(o_p.getSymbol(null));
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IField#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   * @return a non-null object
   */
  public String getSymbol(ISymbolFunction function_p) {
    return getName();
  }
  
}