/**
 * <copyright>
 * 
 * Copyright (c) 2015 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.examples.apa2capella.messages;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("javadoc")
public class Messages extends NLS {
	
	private static final String BUNDLE_NAME = "org.eclipse.emf.diffmerge.bridge.examples.apa2capella.messages.messages"; //$NON-NLS-1$
	
	public static String Apa2CapellaBridgeCommandHandler_BridgeDialogTitle;
	public static String Apa2CapellaBridgeCommandHandler_ExecutionContextNotFound;
	public static String Apa2CapellaBridgeCommandHandler_TargetResourceNotFound;
	public static String Apa2CapellaBridgeJob_JobLabel;
	 
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
