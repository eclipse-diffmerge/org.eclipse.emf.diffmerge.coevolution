/**
 * <copyright>
 * 
 * Copyright (c) 2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.log4j;

import org.apache.log4j.spi.LoggingEvent;

/**
 * A bridge logging event listener
 */
public interface IBridgeLogListener {
  
  /**
   * The appender this listener is listening to.
   */
  static final String APPENDER_NAME = "org.eclipse.emf.diffmerge.bridge.logger"; //$NON-NLS-1$

  /**
   * Handles a logging event
   * 
   * @param event_p (non-null) logging event
   */
  void handleLoggingEvent(LoggingEvent message_p);

}
