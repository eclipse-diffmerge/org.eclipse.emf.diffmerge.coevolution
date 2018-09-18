/*********************************************************************
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
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
  public static String InteractiveEMFBridge_DefaultDialogTitle;
  public static String UpdateDialog_OpenEditorButton;
  
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
