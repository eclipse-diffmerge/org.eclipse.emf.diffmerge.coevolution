/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.impl.emf;

import java.util.Collections;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.impl.scopes.RootedModelScope;
import org.eclipse.emf.ecore.EObject;


/**
 * A partial implementation of IIncrementalBridge.Wrapping for EMF data sets, which does
 * not define how to trace or update elements.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IIncrementalBridge
 * @author Olivier Constant
 */
public abstract class AbstractWrappingIncrementalEMFBridge<SD, TD extends IEditableModelScope>
extends AbstractWrappingIncrementalBridge<SD, TD, IEditableModelScope> {
  
  /**
   * Constructor
   * @param bridge_p a non-null bridge acting as the non-incremental bridge
   */
  public AbstractWrappingIncrementalEMFBridge(IBridge<SD, TD> bridge_p) {
    super(bridge_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#createIntermediateDataSet(java.lang.Object, java.lang.Object)
   */
  public IEditableModelScope createIntermediateDataSet(SD sourceDataSet_p, TD targetDataSet_p) {
    return new RootedModelScope(Collections.<EObject>emptyList());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge#isEmpty(java.lang.Object)
   */
  public boolean isEmpty(TD dataSet_p) {
    return !dataSet_p.getAllContents().hasNext();
  }
  
}
