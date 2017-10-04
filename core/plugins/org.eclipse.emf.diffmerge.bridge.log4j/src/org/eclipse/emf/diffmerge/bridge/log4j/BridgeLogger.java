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

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage;
import org.eclipse.emf.diffmerge.ui.EMFDiffMergeUIPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ILabelProvider;
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
  
  /** The logger for this class */
  protected static final Logger logger = Logger.getLogger(BridgeLogger.class);
  
  /** The singleton instance */
  private static BridgeLogger INSTANCE = null;
  
  
  /**
   * Internal constructor
   */
  protected BridgeLogger() {
    setupConsole();
  }
  
  /**
   * Create and return a new bridge console
   * @param consoleManager_p the (non-null) console manager
   * @return a non-null new bridge console
   */
  protected BridgeConsole createNewConsole(IConsoleManager consoleManager_p) {
    BridgeConsole console = new BridgeConsole(Messages.BridgeLogger_ConsoldeId, null);
    consoleManager_p.addConsoles(new IConsole[] { console });
    console.activate();
    return console;
  }
  
  /**
   * Print the given input message in the console error stream
   * @param message_p (non-null) message
   */
  protected void error(final String message_p) {
    final BridgeConsole console = findConsole();
    console.getErrorStream().println(message_p);
  }
  
  /**
   * Get or create and return the bridge logging console
   * @return a non-null logging console
   */
  protected BridgeConsole findConsole() {
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
   * Return the singleton instance
   * @return a non-null object
   */
  public static BridgeLogger getInstance() { 
    if (INSTANCE == null) {
      INSTANCE = new BridgeLogger();
    }
    return INSTANCE;
  }
  
  /**
   * Return the label provider for representing elements
   * @return a non-null label provider
   */
  protected ILabelProvider getLabelProvider() {
    return EMFDiffMergeUIPlugin.getDefault().getAdapterFactoryLabelProvider();
  }
  
  /**
   * Handle a debug event
   * @param event_p (non-null) logging event
   */
  protected void handleDebugEvent(LoggingEvent event_p) {
    //disabled.
  }
  
  /**
   * Handle an error event
   * @param event_p (non-null) logging event
   */
  protected void handleErrorEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    error(message);
  }
  
  /**
   * Handle a fatal event
   * @param event_p (non-null) logging event
   */
  protected void handleFatalEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    error(message);
  }
  
  /**
   * Handle an information event
   * @param event_p (non-null) logging event
   */
  protected void handleInfoEvent(LoggingEvent event_p) {
    Object message = event_p.getMessage();
    info(message);
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
   * Handle a logging message by appending additional data
   * @param message_p the (non-null) query logging message
   */
  protected void handleLoggingMessage(AbstractLoggingMessage message_p) {
    final BridgeConsole console = findConsole();
    Collection<?> objects = message_p.getObjects();
    for (Object object : objects) {
      registerObject(console, object);
      String sourceName = getLabelProvider().getText(object);
      message_p.mapObjectToLabel(object, sourceName);
    }
  }
  
  /**
   * Handle a warning event
   * @param event_p (non-null) logging event
   */
  protected void handleWarningEvent(LoggingEvent event_p) {
    String message = event_p.getMessage().toString();
    warn(message);
  }
  
  /**
   * Print the given input message in the console info stream
   * @param message_p (non-null) message
   */
  protected void info(final Object message_p) {
    if (message_p instanceof AbstractLoggingMessage)
      handleLoggingMessage((AbstractLoggingMessage) message_p);
    findConsole().getInfoStream().println(message_p.toString());
  }
  
  /**
   * Set the default console water marks, by default use the settings of the standard debug console
   * @param console_p the (non-null) bridge console
   */
  protected void initLimitOutput(BridgeConsole console_p) {
    console_p.setWaterMarks(0, 80000); //limit console output
    console_p.setConsoleWidth(0); //unlimited width
  }
  
  /**
   * Populate the fragment to URI map for the given object
   * @param console_p the (non-null) bridge console
   * @param object_p the (non-null) object whose URI is to map
   */
  private void registerObject(final BridgeConsole console_p, Object object_p) {
    if (object_p instanceof EObject) {
      URI objectURI = EcoreUtil.getURI((EObject) object_p);
      console_p.getFragmentToURIMap().put(objectURI.fragment(), objectURI.trimFragment());
    }
  }
  
  /**
   * Set up the bridge console
   * @return a non-null console
   */
  protected BridgeConsole setupConsole() {
    BridgeConsole console = findConsole();
    initLimitOutput(console);
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
    if (activeWorkbenchWindow != null) {
      IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
      try {
        IConsoleView view = (IConsoleView) activePage.showView(
            Messages.BridgeLogger_ConsoldeId, null, IWorkbenchPage.VIEW_CREATE);
        view.display(console);
      } catch (PartInitException ex) {
        logger.error(ex.getStackTrace(), ex);
      }
    }
    return console;
  }
  
  /**
   * Print the given input message in the console warning stream
   * @param message_p (non-null) message
   */
  protected void warn(final String message_p) {
    final BridgeConsole console = findConsole();
    console.getWarningStream().println(message_p);
  }
  
}