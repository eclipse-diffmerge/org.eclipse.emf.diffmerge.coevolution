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
package org.eclipse.emf.diffmerge.bridge.impl;

import org.apache.log4j.Logger;
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
   * @param logger_p a non-null logger
   */
  public AbstractBridgeTraceExecution(IBridgeTrace.Editable trace_p,
      Logger logger_p) {
    super(logger_p);
    _trace = trace_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution#getTrace()
   */
  public IBridgeTrace.Editable getTrace() {
    return _trace;
  }
  
}
