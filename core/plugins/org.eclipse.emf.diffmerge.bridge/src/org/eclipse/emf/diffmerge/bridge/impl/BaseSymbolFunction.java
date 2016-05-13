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
package org.eclipse.emf.diffmerge.bridge.impl;

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A base implementation of ISymbolFunction.
 */
public class BaseSymbolFunction implements ISymbolFunction {
  
  /**
   * Default constructor
   */
  public BaseSymbolFunction() {
    // Stateless
  }
  
  /**
   * Return a symbolic characterization of the given list
   * @param list_p a non-null list
   * @return a potentially null object
   */
  protected Object getListSymbol(List<?> list_p) {
    StringBuilder builder = new StringBuilder();
    builder.append('(');
    boolean first = true;
    for (Object element : list_p) {
      if (first) {
        first = false;
      } else {
        builder.append(',');
        builder.append(' ');
      }
      Object elementSymbol = getSymbol(element);
      if (elementSymbol == null)
        return null;
      builder.append(elementSymbol);
    }
    builder.append(')');
    Object result = builder.toString();
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction#getSymbol(java.lang.Object)
   */
  public Object getSymbol(Object object_p) {
    Object result = null;
    if (object_p instanceof ISymbolProvider) {
      result = ((ISymbolProvider)object_p).getSymbol(this);
    } else if (object_p instanceof List<?>) {
      result = getListSymbol((List<?>)object_p);
    } else if (object_p instanceof String || object_p instanceof Number) {
      result = object_p.toString();
    }
    return result;
  }
  
}