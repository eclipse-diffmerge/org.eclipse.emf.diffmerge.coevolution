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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

/**
 * The incremental bridge console
 */
public class BridgeConsole extends MessageConsole {
  
  /**
   * The logger of this class
   */
  static final Logger logger = Logger.getLogger(BridgeConsole.class);
  /**
   * Info stream
   */
  private MessageConsoleStream _infoStream;
  /**
   * Warning stream
   */
  private MessageConsoleStream _warningStream;
  /**
   * Error stream
   */
  private MessageConsoleStream _errorStream;
  /**
   * Console line notifier
   */
  private final PatternMatchListener _notifier;
  /**
   * The fragment to uri map
   */
  private Map<String, URI> fragmentToURIMap = new HashMap<String, URI>();
  
  /**
   * Default constructor
   * 
   * @param name_p (non-null) console name
   * @param imageDescriptor_p possibly null image descriptor
   */
  public BridgeConsole(String name_p, ImageDescriptor imageDescriptor_p) {
    super(name_p, imageDescriptor_p);
    Display display = PlatformUI.getWorkbench().getDisplay();
    initializeInfoStream(display);
    initializeWarningStream(display);
    initializeErrorStream(display);
    _notifier = new PatternMatchListener();
    addPatternMatchListener(_notifier);
  }

  /**
   * Returns the console info stream
   * 
   * @return the (non-null) info stream
   */
  public MessageConsoleStream getInfoStream() {
    return _infoStream;
  }

  /**
   * Returns the console warning stream
   * 
   * @return the (non-null) warning stream
   */
  public MessageConsoleStream getWarningStream() {
    return _warningStream;
  }

  /**
   * Returns the console error stream
   * 
   * @return the (non-null) error stream
   */
  public MessageConsoleStream getErrorStream() {
    return _errorStream;
  }

  /**
   * Creates and initializes the info stream with color and font style
   * 
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeInfoStream(Display display_p) {
    Color color = new Color(display_p, new RGB(0, 0, 0));
    _infoStream = newMessageStream();
    _infoStream.setColor(color);
  }

  /**
   * Creates and initializes the warning stream with color and font style
   * 
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeWarningStream(Display display_p) {
    Color color = new Color(display_p, new RGB(255, 127, 80));
    _warningStream = newMessageStream();
    _warningStream.setColor(color);
  }

  /**
   * Creates and initializes the error stream with color and font style
   * 
   * @param display_p (non-null) the device on which to allocate the color
   */
  private void initializeErrorStream(Display display_p) {
    Color color = new Color(display_p, new RGB(255, 0, 0));
    _errorStream = newMessageStream();
    _errorStream.setColor(color);
  }
  
  /**
   * @see org.eclipse.ui.console.IOConsole#dispose()
   */
  @Override
  protected void dispose() {
    super.dispose();
    try {
      removePatternMatchListener(_notifier);
      _infoStream.getColor().dispose();
      _infoStream.close();
      _warningStream.getColor().dispose();
      _warningStream.close();
      _errorStream.getColor().dispose();
      _errorStream.close();
    } catch (IOException ex) {
      logger.error(ex.getStackTrace(), ex);
    }
  }

  /**
   * @return the fragment to uri map
   */
  public Map<String, URI> getFragmentToURIMap() {
    return fragmentToURIMap;
  }

  /**
   * Console line notifier
   */
  public static class PatternMatchListener implements IPatternMatchListener {

    /**
     * The pattern to detect identifiable elements written in console between [ and ]
     */
    private static final String BRIDGE_ELEMENT_ID_PATTERN = "\\[([a-zA-Z\\d_$-][a-zA-Z\\d_$-]*\\.)*[a-zA-Z\\d_$-][a-zA-Z\\d_$-]*\\]"; //$NON-NLS-1$
    /**
     * The console this notifier is tracking 
     */
    private TextConsole _console = null;

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
        if (uri != null) {
          BridgeElementLink link = new BridgeElementLink(uri.appendFragment(identifierWithoutBrackets));
          _console.addHyperlink(link, offset + 1, identifierWithoutBrackets.length());
        }
      } catch (BadLocationException ex) {
        logger.error(ex.getStackTrace(), ex);
      }
    }

    /**
     * @see org.eclipse.ui.console.IPatternMatchListener#getPattern()
     */
    public String getPattern() {
      return BRIDGE_ELEMENT_ID_PATTERN;
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
  }
}