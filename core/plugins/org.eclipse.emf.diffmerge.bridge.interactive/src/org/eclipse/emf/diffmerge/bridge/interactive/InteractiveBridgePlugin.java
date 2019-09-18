/*********************************************************************
 * Copyright (c) 2014-2019 Thales Global Services S.A.S.
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

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class for this plug-in.
 * @author Olivier Constant
 */
public class InteractiveBridgePlugin extends AbstractUIPlugin {
  
	/**  The shared instance */
	private static InteractiveBridgePlugin __plugin;
	
	
	/**
	 * Constructor
	 */
	public InteractiveBridgePlugin() {
    // Nothing needed
	}
	
  /**
   * Returns the shared instance of this activator
   * @return a non-null instance
   */
  public static InteractiveBridgePlugin getDefault() {
    return __plugin;
  }
  
  /**
   * Get the plug-in ID according to MANIFEST.MF
   * @return a non-null String
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }
  
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		__plugin = this;
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		__plugin = null;
		super.stop(context);
	}
	
}
