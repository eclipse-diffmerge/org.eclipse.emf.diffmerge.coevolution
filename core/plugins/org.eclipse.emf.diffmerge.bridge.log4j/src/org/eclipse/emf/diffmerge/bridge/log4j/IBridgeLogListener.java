/*********************************************************************
 * Copyright (c) 2017-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
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
