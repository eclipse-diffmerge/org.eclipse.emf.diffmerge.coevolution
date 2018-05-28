/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
