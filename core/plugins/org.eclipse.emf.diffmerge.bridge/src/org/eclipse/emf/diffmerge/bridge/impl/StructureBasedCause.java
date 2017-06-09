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
package org.eclipse.emf.diffmerge.bridge.impl;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.api.ICause.Symbolic;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;


/**
 * A symbolic cause that is further specified by a slot in a structure.
 * @see IPureStructure
 */
public class StructureBasedCause implements Symbolic<Object> {
  
  /** The non-null base cause or a symbol that represents it */
  private final Object _baseCause;
  
  /** The non-null slot */
  private final Object _slot;
  
  
  /**
   * Constructor
   * @param baseCause_p the non-null base cause
   * @param slot_p the non-null slot identifier
   */
  public StructureBasedCause(Object baseCause_p, Object slot_p) {
    _baseCause = baseCause_p;
    _slot = slot_p;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StructureBasedCause other = (StructureBasedCause) obj;
    if (_baseCause == null) {
      if (other._baseCause != null)
        return false;
    } else if (!_baseCause.equals(other._baseCause))
      return false;
    if (_slot == null) {
      if (other._slot != null)
        return false;
    } else if (!_slot.equals(other._slot))
      return false;
    return true;
  }
  
  /**
   * Return the base cause
   * @return a non-null object
   */
  public Object getBaseCause() {
    return _baseCause;
  }
  
  /**
   * Return the slot
   * @return a non-null object
   */
  public Object getSlot() {
    return _slot;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ICause#getSourceElements()
   */
  public Collection<?> getSourceElements() {
    Collection<?> result;
    if (_baseCause instanceof ICause<?>)
      result = ((ICause<?>)_baseCause).getSourceElements();
    else
      result = Collections.emptySet();
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    Object baseSymbol;
    if (_baseCause instanceof ICause.Symbolic<?>)
      baseSymbol = ((ICause.Symbolic<?>)_baseCause).getSymbol(function_p);
    else
      baseSymbol = function_p.getSymbol(_baseCause);
    Object slotSymbol = function_p.getSymbol(_slot);
    Object result;
    if (baseSymbol == null || slotSymbol == null)
      result = null;
    else
      result = baseSymbol.toString() + '|' + slotSymbol;
    return result;
  }
  
  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((_baseCause == null) ? 0 : _baseCause.hashCode());
    result = prime * result + ((_slot == null) ? 0 : _slot.hashCode());
    return result;
  }
  
}