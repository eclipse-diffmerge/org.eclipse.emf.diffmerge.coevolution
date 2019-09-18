/*********************************************************************
 * Copyright (c) 2017-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.util;

import org.eclipse.emf.diffmerge.api.scopes.IModelScope;


/**
 * A model scope which can be set in a normalized form.
 * @author Olivier Constant
 */
public interface INormalizableModelScope extends IModelScope {
  
  /**
   * Set the scope in normalized form.
   */
  void normalize();
  
}
