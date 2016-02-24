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
package org.eclipse.emf.diffmerge.bridge.incremental;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;


/**
 * A model scope which is dedicated to holding intermediate targets in incremental transformations.
 * @author Olivier Constant
 */
public class IntermediateModelScope extends RootedNormalizableModelScope {
  
  /**
   * Constructor
   */
  public IntermediateModelScope() {
    super(Collections.<EObject>emptyList());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.impl.scopes.AbstractModelScope#getOriginator()
   */
  @Override
  public Object getOriginator() {
    return "EXPECTED"; //$NON-NLS-1$
  }
  
}
