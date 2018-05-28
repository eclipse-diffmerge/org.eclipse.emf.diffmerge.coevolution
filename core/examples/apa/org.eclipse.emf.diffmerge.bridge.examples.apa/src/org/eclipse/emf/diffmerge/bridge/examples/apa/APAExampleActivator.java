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
package org.eclipse.emf.diffmerge.bridge.examples.apa;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class APAExampleActivator extends AbstractUIPlugin {

	/** The shared instance */
	private static APAExampleActivator plugin;
	
	
	/**
	 * The constructor
	 */
	public APAExampleActivator() {
	}
	
  /**
   * Returns the shared instance
   */
  public static APAExampleActivator getDefault() {
    return plugin;
  }
  
  /**
   * Return a default GUI label
   * @return a non-null string
   */
  public String getLabel() {
    return Messages.APAExampleActivator_Label;
  }
  
  /**
   * Return the ID of this plug-in according to MANIFEST.MF
   * @return a non-null string
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
		plugin = this;
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
  @Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
  
}
