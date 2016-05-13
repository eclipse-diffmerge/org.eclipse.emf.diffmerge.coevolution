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

import org.eclipse.emf.diffmerge.bridge.api.ICause.Symbolic;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;


/**
 * A symbolic cause that is further specified by a slot in a structure.
 * @see IPureStructure
 */
public class StructureBasedCause implements Symbolic<Object, Object> {
  
  /** The non-null base cause */
  private final Symbolic<?,?> _baseCause;
  
  /** The non-null slot */
  private final Object _slot;
  
  
  /**
   * Constructor
   * @param baseCause_p the non-null base cause
   * @param slot_p the non-null slot identifier
   */
  public StructureBasedCause(Symbolic<?,?> baseCause_p, Object slot_p) {
    _baseCause = baseCause_p;
    _slot = slot_p;
  }
  
  /**
   * Return the base cause
   * @return a non-null object
   */
  public Symbolic<?,?> getBaseCause() {
    return _baseCause;
  }
  
  /**
   * Return the slot
   */
  public Object getSlot() {
    return _slot;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    Object baseSymbol = _baseCause.getSymbol(function_p);
    Object slotSymbol = function_p.getSymbol(_slot);
    Object result;
    if (baseSymbol == null || slotSymbol == null)
      result = null;
    else
      result = baseSymbol.toString() + '|' + slotSymbol;
    return result;
  }
  
}