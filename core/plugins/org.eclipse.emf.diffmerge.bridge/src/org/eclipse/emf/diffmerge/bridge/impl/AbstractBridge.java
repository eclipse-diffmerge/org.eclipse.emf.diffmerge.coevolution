/*********************************************************************
 * Copyright (c) 2018 Thales Global Services S.A.S.
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

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;


/**
 * A base implementation of IBridge.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @author Olivier Constant
 */
public abstract class AbstractBridge<SD, TD> implements IBridge<SD, TD> {
  
  /** The logger property key */
  protected static final String LOGGER_CONFIG_KEY_LOGGER =
      "log4j.logger.org.eclipse.emf.diffmerge"; //$NON-NLS-1$
  
  /** The appender property key */
  protected static final String LOGGER_CONFIG_KEY_APPENDER =
      "log4j.appender.coevolution"; //$NON-NLS-1$
  
  /** The conversion pattern property key */
  protected static final String LOGGER_CONFIG_KEY_CONVERSIONPATTERN =
      "log4j.appender.coevolution.layout.ConversionPattern"; //$NON-NLS-1$
  
  /** The layout property key */
  protected static final String LOGGER_CONFIG_KEY_LAYOUT =
      "log4j.appender.coevolution.layout"; //$NON-NLS-1$
  
  /** The threshold property key */
  protected static final String LOGGER_CONFIG_KEY_THRESHOLD =
      "log4j.appender.coevolution.threshold"; //$NON-NLS-1$
  
  /** The appender class name */
  protected static final String LOGGER_CONFIG_VALUE_APPENDER =
      "org.eclipse.emf.diffmerge.bridge.log4j.BridgeLoggingAppender"; //$NON-NLS-1$
  
  /** The non-null logger associated to this class. */
  private static final Logger logger = Logger.getLogger(AbstractBridge.class);
  
  /** Whether the log system has been set up */
  protected static boolean __logSetUp = false;
  
  
  /**
   * Constructor
   */
  public AbstractBridge() {
    checkLoggerSetup();
  }
  
  /**
   * Check that the log system has been set up
   */
  protected void checkLoggerSetup() {
    if (!__logSetUp) {
      setupLogger();
      logger.setLevel(Level.OFF); // Initially no bridge logging
      __logSetUp = true;
    }
  }
  
  /**
   * Return a logger for this bridge
   * @return a non-null logger
   */
  public Logger getLogger() {
    return logger;
  }
  
  /**
   * Set up the log system
   */
  protected void setupLogger() {
    LogManager.resetConfiguration();
    Properties properties = new Properties();
    properties.setProperty(LOGGER_CONFIG_KEY_LOGGER, "DEBUG, coevolution"); //$NON-NLS-1$
    properties.setProperty(LOGGER_CONFIG_KEY_APPENDER, LOGGER_CONFIG_VALUE_APPENDER);
    properties.setProperty(LOGGER_CONFIG_KEY_LAYOUT, PatternLayout.class.getName());
    properties.setProperty(LOGGER_CONFIG_KEY_THRESHOLD, "DEBUG"); //$NON-NLS-1$
    properties.setProperty(LOGGER_CONFIG_KEY_CONVERSIONPATTERN, "%-5p [%t]: %m%n"); //$NON-NLS-1$
    PropertyConfigurator.configure(properties);
  }
  
}
