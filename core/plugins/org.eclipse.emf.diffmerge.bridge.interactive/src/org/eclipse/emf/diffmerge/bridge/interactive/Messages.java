/**
 * <copyright>
 * 
 * Copyright (c) 2014 Thales Global Services S.A.S.
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
  public static String BridgeJob_ActionText;
  public static String BridgeJob_DefaultName;
  public static String BridgeJob_KeepOpen_Message;
  public static String BridgeJob_KeepOpen_Title;
  public static String BridgeJob_Step_Completion;
  public static String BridgeJob_Step_Execution;
  public static String BridgeJob_Step_InteractiveUpdate;
  public static String BridgeJob_Step_SetUp;
  public static String InteractiveEMFBridge_DefaultDialogTitle;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
