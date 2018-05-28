/**
 * <copyright>
 * 
 * Copyright (c) 2017-2018 Thales Global Services S.A.S.
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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;


/**
 * A logging appender which propagates logging events to its registered listeners.
 */
public class BridgeLoggingAppender extends AppenderSkeleton{

  /** The logging message pattern */
  private static final String LOG_PATTERN = "%m%n"; //$NON-NLS-1$
  
  /** The log4j appender name */
  private static final String APPENDER_NAME = "coevolution"; //$NON-NLS-1$
  
  /** The bridge log listeners */
  private final List<IBridgeLogListener> _listeners = new ArrayList<IBridgeLogListener>();
  
  
  /**
   * Default constructor
   */
  public BridgeLoggingAppender() {
    layout = new PatternLayout(LOG_PATTERN);
    setName(APPENDER_NAME);
    addBridgeLogListener(BridgeLogger.getInstance());
  }
  
  /**
   * Add a listener to the bridge console listeners list
   * @param listener_p a (non-null) bridge console listener
   */
  public void addBridgeLogListener(final IBridgeLogListener listener_p) {
    if (null != listener_p) {
      _listeners.add(listener_p);
    }
  }
  
  /**
   * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
   */
  @Override
  protected void append(LoggingEvent event_p) {
    notifyListeners(event_p);
  }
  
  /**
   * @see org.apache.log4j.AppenderSkeleton#close()
   */
  public void close() {
    removeBridgeLogListener(BridgeLogger.getInstance());
    layout = null;
  }
  
  /**
   * @see org.apache.log4j.AppenderSkeleton#doAppend(org.apache.log4j.spi.LoggingEvent)
   */
  @Override
  public synchronized void doAppend(LoggingEvent event_p) {
    super.doAppend(event_p);
  }
  
  /**
   * Notify the listeners of this appender
   * @param event_p (non-null) event
   */
  protected void notifyListeners(LoggingEvent event_p) {
    for (IBridgeLogListener listener : _listeners) {
      listener.handleLoggingEvent(event_p);
    }
  }
  
  /**
   * Remove the listener from the bridge console listeners list
   * @param listener_p a (non-null) bridge console listener
   */
  public void removeBridgeLogListener(final IBridgeLogListener listener_p) {
    if (null != listener_p) {
      _listeners.remove(listener_p);
    }
  }
  
  /**
   * @see org.apache.log4j.AppenderSkeleton#requiresLayout()
   */
  public boolean requiresLayout() {
    return true;
  }
  
}