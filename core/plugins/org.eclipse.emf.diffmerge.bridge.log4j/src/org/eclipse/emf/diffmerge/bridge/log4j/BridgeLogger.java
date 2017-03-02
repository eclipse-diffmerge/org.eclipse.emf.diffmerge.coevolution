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
package org.eclipse.emf.diffmerge.bridge.log4j;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.diffmerge.bridge.mapping.util.QueryLoggingMessage;
import org.eclipse.emf.diffmerge.bridge.mapping.util.RuleLoggingMessage;
import org.eclipse.emf.diffmerge.bridge.util.BaseTraceLoggingMessage;
import org.eclipse.emf.diffmerge.ui.EMFDiffMergeUIPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;

/**
 * A singleton to be used to log bridge executions.
 */
public class BridgeLogger implements IBridgeLogListener {
  
  /**
   * The logger for this class
   */
  static final Logger logger = Logger.getLogger(BridgeLogger.class);
  /**
   * The singleton instance
   */
  private static BridgeLogger INSTANCE = null;

  /**
   * Internal constructor
   */
  private BridgeLogger() {
    setupConsole();
  }

  /**
   * @return the singleton instance
   */
  public static BridgeLogger getInstance() { 
    if (INSTANCE == null) {
      INSTANCE = new BridgeLogger();
    }
    return INSTANCE;
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.log4j.IBridgeLogListener#handleLoggingEvent(org.apache.log4j.spi.LoggingEvent)
   */
  public void handleLoggingEvent(LoggingEvent event_p) {
    Level level = event_p.getLevel();
    switch(level.toInt()) {
      case Priority.DEBUG_INT : 
        handleDebugEvent(event_p); 
        break;
      case Priority.ERROR_INT :
        handleErrorEvent(event_p); 
        break;
      case Priority.FATAL_INT :
        handleFatalEvent(event_p); 
        break;
      case Priority.INFO_INT :
        handleInfoEvent(event_p); 
        break;
      case Priority.WARN_INT : 
        handleWarningEvent(event_p); 
        break;
      default: break;
    }
  }

  /**
   * Prints the input message in the console info stream
   * 
   * @param message_p (non-null) message
   */
  private void info(final Object message_p) {
    if (message_p instanceof BaseTraceLoggingMessage) {
      handleTraceLoggingMessage((BaseTraceLoggingMessage) message_p);
    }
    else if (message_p instanceof QueryLoggingMessage) {
      handleQueryLoggingMessage((QueryLoggingMessage) message_p);
    }
    else if (message_p instanceof RuleLoggingMessage) {
      handleRuleLoggingMessage((RuleLoggingMessage) message_p);
    }
    findConsole().getInfoStream().println(message_p.toString());
  }

  /**
   * Prints the input message in the console warning stream
   * 
   * @param message_p (non-null) message
   */
  private void warn(final String message_p) {
    final BridgeConsole console = findConsole();
    console.getWarningStream().println(message_p);
  }

  /**
   * Prints the input message in the console error stream
   *  
   * @param message_p (non-null) message
   */
  private void error(final String message_p) {
    final BridgeConsole console = findConsole();
    console.getErrorStream().println(message_p);
  }

  /**
   * Handles a warning event
   * 
   * @param event_p (non-null) logging event
   */
  private void handleWarningEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    warn(message);
  }

  /**
   * Handles an information event
   * 
   * @param event_p (non-null) logging event
   */
  private void handleInfoEvent(LoggingEvent event_p) {
    Object message = event_p.getMessage();
    info(message);
  }

  /**
   * Handles a fatal event
   * 
   * @param event_p (non-null) logging event
   */
  private void handleFatalEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    error(message);
  }

  /**
   * Handles an error event
   * 
   * @param event_p (non-null) logging event
   */
  private void handleErrorEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    error(message);
  }

  /**
   * Handles a debug event
   * 
   * @param event_p (non-null) logging event
   */
  private void handleDebugEvent(LoggingEvent event_p) {
    //disabled.
  }

  /**
   * Handles a query logging message by appending additional data.
   * 
   * @param message_p the (non-null) query logging message
   */
  private void handleQueryLoggingMessage(QueryLoggingMessage message_p) {
    final BridgeConsole console = findConsole();
    Object queryResult = message_p.getQueryResult();
    registerObject(console, queryResult);
    String objectLabel = EMFDiffMergeUIPlugin.getDefault().getAdapterFactoryLabelProvider().getText(queryResult);
    message_p.mapObjectToLabel(queryResult, objectLabel);
  }

  /**
   * Handles a rule logging message by appending additional data.
   * 
   * @param message_p the (non-null) rule logging message
   */
  private void handleRuleLoggingMessage(RuleLoggingMessage message_p) {
    //do nothing.
  }

  /**
   * Builds a custom logging message for EMF-based bridge traces.
   * 
   * @param message_p (non-null) base logging message
   */
  private void handleTraceLoggingMessage(final BaseTraceLoggingMessage message_p) {
    final BridgeConsole console = findConsole();
    List<Object> sources = message_p.getCause().getSourceElements();
    Object target = message_p.getTarget();
    registerObject(console, target);
    String targetName = EMFDiffMergeUIPlugin.getDefault().getAdapterFactoryLabelProvider().getText(target);
    message_p.mapObjectToLabel(target, targetName);
    for (Object source : sources) {
      registerObject(console, source);
      String sourceName = EMFDiffMergeUIPlugin.getDefault().getAdapterFactoryLabelProvider().getText(source);
      message_p.mapObjectToLabel(source, sourceName);
    }
  }

  /**
   * Populates the fragment to uri map for the given object
   * @param console_p
   * @param object_p
   */
  private void registerObject(final BridgeConsole console_p, Object object_p) {
    if (object_p instanceof EObject) {
      // map source fragments to complete URI
      URI sourceURI = EcoreUtil.getURI((EObject) object_p);
      console_p.getFragmentToURIMap().put(sourceURI.fragment(), sourceURI.trimFragment());
    }
  }

  /**
   * Sets up the bridge console
   */
  private BridgeConsole setupConsole() {
    BridgeConsole console = findConsole();
    console.setWaterMarks(-1, -1);
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
    if (activeWorkbenchWindow != null) {
      IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
      try {
        IConsoleView view = (IConsoleView) activePage.showView(Messages.BridgeLogger_ConsoldeId);
        view.display(console);
      } catch (PartInitException ex) {
        logger.error(ex.getStackTrace(), ex);
      }
    }
    return console;
  }

  /**
   * Gets or creates the bridge logging console
   * 
   * @return the bridge logging console
   */
  private BridgeConsole findConsole() {
    IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
    IConsole[] existing = consoleManager.getConsoles();
    for (int i = 0; i < existing.length; i++) {
      if (Messages.BridgeLogger_ConsoldeId.equals(existing[i].getName())) {
        BridgeConsole console = (BridgeConsole) existing[i];
        consoleManager.showConsoleView(console);
        return console;
      }
    }
    return createNewConsole(consoleManager);
  }

  /**
   * Creates a new bridge console
   * 
   * @param consoleManager_p the (non-null) console manager
   * @return a new bridge console
   */
  private BridgeConsole createNewConsole(IConsoleManager consoleManager_p) {
    BridgeConsole console = new BridgeConsole(Messages.BridgeLogger_ConsoldeId, null);
    consoleManager_p.addConsoles(new IConsole[] { console });
    return console;
  }
}