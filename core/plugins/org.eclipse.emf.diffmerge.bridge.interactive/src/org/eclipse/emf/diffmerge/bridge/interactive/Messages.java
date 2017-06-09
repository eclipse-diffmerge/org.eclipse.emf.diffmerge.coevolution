/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.interactive;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("javadoc")
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "org.eclipse.emf.diffmerge.bridge.interactive.messages"; //$NON-NLS-1$
  public static String BridgeComparisonViewer_ReferenceLabel;
  public static String BridgeComparisonViewer_TargetLabel;
  public static String BridgeJob_ActionText;
  public static String BridgeJob_DefaultName;
  public static String BridgeJob_Step_Completion;
  public static String BridgeJob_Step_Execution;
  public static String BridgeJob_Step_InteractiveUpdate;
  public static String BridgeJob_Step_SetUp;
  public static String BridgeLogger_InteractiveMergeStepMessage;
  public static String BridgeLoggerConfig_AppenderKey;
  public static String BridgeLoggerConfig_AppenderValue;
  public static String BridgeLoggerConfig_ConversionPatternKey;
  public static String BridgeLoggerConfig_ConversionPatternValue;
  public static String BridgeLoggerConfig_DisabledLoggerWarning;
  public static String BridgeLoggerConfig_LayoutKey;
  public static String BridgeLoggerConfig_LayoutValue;
  public static String BridgeLoggerConfig_LoggerKey;
  public static String BridgeLoggerConfig_LoggerValue;
  public static String BridgeLoggerConfig_TresholdKey;
  public static String BridgeLoggerConfig_TresholdValue;
  public static String InteractiveEMFBridge_DefaultDialogTitle;
  public static String UpdateDialog_Defer;
  public static String UpdateDialog_OpenEditorButton;
  
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
