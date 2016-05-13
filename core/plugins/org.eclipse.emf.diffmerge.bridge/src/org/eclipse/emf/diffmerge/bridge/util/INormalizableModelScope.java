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
package org.eclipse.emf.diffmerge.bridge.util;

import org.eclipse.emf.diffmerge.api.scopes.IModelScope;


/**
 * A model scope which can be set in normalized form.
 * @author Olivier Constant
 */
public interface INormalizableModelScope extends IModelScope {
  
  /**
   * Set the scope in normalized form.
   */
  void normalize();
  
}
