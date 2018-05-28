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
package org.eclipse.emf.diffmerge.bridge.impl;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;


/**
 * An implementation of IBridgeExecution with a directly owned trace.
 * @author Olivier Constant
 */
public abstract class AbstractBridgeTraceExecution extends AbstractBridgeExecution {
  
  /** The optional trace that reflects this execution */
  private final IBridgeTrace.Editable _trace;
  
  
  /**
   * Constructor
   * @param trace_p the optional trace that reflects this execution
   */
  public AbstractBridgeTraceExecution(IBridgeTrace.Editable trace_p) {
    _trace = trace_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#getTrace()
   */
  public IBridgeTrace.Editable getTrace() {
    return _trace;
  }
  
}
