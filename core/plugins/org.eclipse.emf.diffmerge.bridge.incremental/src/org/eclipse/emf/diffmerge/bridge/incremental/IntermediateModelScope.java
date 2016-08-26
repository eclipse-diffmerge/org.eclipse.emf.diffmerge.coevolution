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
package org.eclipse.emf.diffmerge.bridge.incremental;

import java.util.Collections;

import org.eclipse.emf.diffmerge.api.scopes.IModelScope;
import org.eclipse.emf.ecore.EObject;


/**
 * A model scope which is dedicated to holding intermediate targets in incremental transformations.
 * @author Olivier Constant
 */
public class IntermediateModelScope extends RootedNormalizableModelScope {
  
  /** The optional source data set */
  protected final Object _sourceDataSet;
  
  /** The optional target data set */
  protected final IModelScope _targetDataSet;
  
  
  /**
   * Constructor
   */
  public IntermediateModelScope() {
    this(null, null);
  }
  
  /**
   * Constructor
   */
  public IntermediateModelScope(Object sourceDataSet_p, IModelScope targetDataSet_p) {
    super(Collections.<EObject>emptyList());
    _sourceDataSet = sourceDataSet_p;
    _targetDataSet = targetDataSet_p;
  }
  
}
