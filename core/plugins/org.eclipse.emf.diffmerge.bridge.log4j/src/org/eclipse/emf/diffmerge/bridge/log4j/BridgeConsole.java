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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;


/**
 * The incremental bridge console.
 */
public class BridgeConsole extends MessageConsole {
  
  /** The logger for this class */
  protected static final Logger logger = Logger.getLogger(BridgeConsole.class);
  
  /** The non-null info stream */
  private MessageConsoleStream _infoStream;
  
  /** The non-null warning stream */
  private MessageConsoleStream _warningStream;
  
  /** The non-null error stream */
  private MessageConsoleStream _errorStream;
  
  /** The console line notifier */
  private final PatternMatchListener _notifier;
  
  /** The non-null fragment to URI map */
  private Map<String, URI> _fragmentToURIMap;
  
  
  /**
   * Constructor
   * @param name_p a (non-null) console name
   * @param imageDescriptor_p a possibly null image descriptor
   */
  public BridgeConsole(String name_p, ImageDescriptor imageDescriptor_p) {
    super(name_p, imageDescriptor_p);
    _fragmentToURIMap = new HashMap<String, URI>();
    Display display = PlatformUI.getWorkbench().getDisplay();
    initializeInfoStream(display);
    initializeWarningStream(display);
    initializeErrorStream(display);
    _notifier = new PatternMatchListener();
    addPatternMatchListener(_notifier);
  }
  
  /**
   * @see org.eclipse.ui.console.IOConsole#dispose()
   */
  @Override
  protected void dispose() {
    super.dispose();
    try {
      removePatternMatchListener(_notifier);
      _infoStream.flush();
      _infoStream.close();
      _warningStream.flush();
      _warningStream.close();
      _errorStream.flush();
      _errorStream.close();
    } catch (IOException ex) {
      logger.error(ex.getStackTrace(), ex);
    }
  }
  
  /**
   * Return the console error stream
   * @return the (non-null) error stream
   */
  public MessageConsoleStream getErrorStream() {
    return _errorStream;
  }
  
  /**
   * Return the fragment to URI map
   * @return a non-null map
   */
  public Map<String, URI> getFragmentToURIMap() {
    return _fragmentToURIMap;
  }
  
  /**
   * Return the console info stream
   * @return the (non-null) info stream
   */
  public MessageConsoleStream getInfoStream() {
    return _infoStream;
  }
  
  /**
   * Return the console warning stream
   * @return the (non-null) warning stream
   */
  public MessageConsoleStream getWarningStream() {
    return _warningStream;
  }
  
  /**
   * Create and initialize the info stream with color and font style
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeInfoStream(Display display_p) {
    Color color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
    _infoStream = newMessageStream();
    _infoStream.setColor(color);
  }
  
  /**
   * Create and initialize the warning stream with color and font style
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeWarningStream(Display display_p) {
    Color color = Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW);
    _warningStream = newMessageStream();
    _warningStream.setColor(color);
  }
  
  /**
   * Create and initialize the error stream with color and font style
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeErrorStream(Display display_p) {
    Color color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
    _errorStream = newMessageStream();
    _errorStream.setColor(color);
  }
  
  
  /**
   * The console line notifier.
   */
  public static class PatternMatchListener implements IPatternMatchListener {
    /** The pattern to detect identifiers written in console between [ and ] */
    private static final String BRIDGE_ELEMENT_ID_PATTERN =
        "\\[([a-zA-Z\\d_$-][a-zA-Z\\d_$-]*\\.)*[a-zA-Z\\d_$-][a-zA-Z\\d_$-]*\\]"; //$NON-NLS-1$
    
    /** The pattern to detect fragments in the form //@model/@features.0/@... between [ and ] */
    private static final String BRIDGE_ELEMENT_FRAGMENT_PATTERN =
        "\\[(\\/(\\/)?\\@[a-zA-Z\\d_][a-zA-Z\\d_]*(\\.[0-9]+)?)+\\]"; //$NON-NLS-1$
    
    /** The logical OR of the patterns previously defined */
    private static final String BRIDGE_ELEMENT_COMBINED_PATTERN =
        "(" + BRIDGE_ELEMENT_ID_PATTERN + "|" + BRIDGE_ELEMENT_FRAGMENT_PATTERN + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    
    /** The potentially null console this notifier is tracking */
    private TextConsole _console;
    
    /**
     * Constructor
     */
    public PatternMatchListener() {
      _console = null;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#connect(org.eclipse.ui.console.TextConsole)
     */
    public void connect(TextConsole console_p) {
      _console = console_p;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#disconnect()
     */
    public void disconnect() {
      _console = null;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListener#getCompilerFlags()
     */
    public int getCompilerFlags() {
      return Pattern.DOTALL;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListener#getLineQualifier()
     */
    public String getLineQualifier() {
      return null;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListener#getPattern()
     */
    public String getPattern() {
      return BRIDGE_ELEMENT_COMBINED_PATTERN;
    }
    
    /**
     * Return whether the given URI is a valid one
     * @param uri_p the potentially null URI to consider
     * @return whether the URI is valid
     */
    public boolean isValid(URI uri_p) {
      return uri_p != null && !uri_p.isEmpty() && uri_p.scheme()!=null;
    }
    
    /**
     * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#matchFound(org.eclipse.ui.console.PatternMatchEvent)
     */
    public void matchFound(PatternMatchEvent event_p) {
      int offset = event_p.getOffset();
      int length = event_p.getLength();
      try {
        IDocument document = _console.getDocument();
        String identifier = document.get(offset, length);
        String identifierWithoutBrackets = identifier.substring(1, identifier.length() - 1);
        URI uri = ((BridgeConsole) _console).getFragmentToURIMap().get(identifierWithoutBrackets);
        if (isValid(uri)) {
          BridgeElementLink link = new BridgeElementLink(uri.appendFragment(identifierWithoutBrackets));
          _console.addHyperlink(link, offset + 1, identifierWithoutBrackets.length());
        }
      } catch (BadLocationException ex) {
        logger.error(ex.getStackTrace(), ex);
      }
    }
  }
  
}