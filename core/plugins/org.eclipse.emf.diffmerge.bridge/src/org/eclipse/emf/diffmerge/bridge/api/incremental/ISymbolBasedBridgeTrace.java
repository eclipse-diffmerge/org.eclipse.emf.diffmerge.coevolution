/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.api.incremental;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;


/**
 * An IBridgeTrace that relies on simple symbols that uniquely characterize
 * target data elements and the cause of their presence.
 * @author Olivier Constant
 */
public interface ISymbolBasedBridgeTrace extends IBridgeTrace {
  
  /**
   * Return the symbol function for the trace
   * @return a non-null object
   */
  ISymbolFunction getSymbolFunction();
  
  
  /**
   * An ISymbolBasedBridgeTrace that can be modified.
   */
  interface Editable extends ISymbolBasedBridgeTrace, IBridgeTrace.Editable {
    /**
     * Set the given symbol function as the symbol function of this trace
     * @param function_p a non-null object
     */
    void setSymbolFunction(ISymbolFunction function_p);
  }
  
}
