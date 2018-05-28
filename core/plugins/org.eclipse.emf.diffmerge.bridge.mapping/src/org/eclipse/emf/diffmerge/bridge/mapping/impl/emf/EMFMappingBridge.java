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
package org.eclipse.emf.diffmerge.bridge.mapping.impl.emf;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingBridge;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.MappingBridge;


/**
 * An implementation of IMappingBridge with an EMF model scope as target.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IMappingBridge
 * @author Olivier Constant
 */
public class EMFMappingBridge<SD, TD extends IEditableModelScope>
extends MappingBridge<SD, TD> {
  
  /**
   * Constructor
   */
  public EMFMappingBridge() {
    super();
  }
  
}
