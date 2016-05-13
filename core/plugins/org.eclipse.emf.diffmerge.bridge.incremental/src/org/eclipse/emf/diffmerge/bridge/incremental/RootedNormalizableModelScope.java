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

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.util.INormalizableModelScope;
import org.eclipse.emf.diffmerge.impl.scopes.RootedModelScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * A model scope which is dedicated to holding intermediate targets in incremental transformations.
 * @author Olivier Constant
 */
public class RootedNormalizableModelScope extends RootedModelScope implements INormalizableModelScope {
  
  /**
   * Constructor
   * @param roots_p a non-null list of elements whose containment trees are disjoint,
   *        that is, no element is the ancestor of another one
   */
  public RootedNormalizableModelScope(List<? extends EObject> roots_p) {
    super(roots_p);
  }
  
  /**
   * Constructor
   * @param roots_p a non-null list of elements whose containment trees are disjoint,
   *        that is, no element is the ancestor of another one
   * @param operateOnList_p whether the list must be used directly as the root container,
   *        where true implies that roots_p is modifiable
   */
  public RootedNormalizableModelScope(List<EObject> roots_p, boolean operateOnList_p) {
    super(roots_p, operateOnList_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.INormalizableModelScope#normalize()
   */
  public void normalize() {
    List<EObject> filtered = EcoreUtil.filterDescendants(getContents());
    _roots.retainAll(filtered);
  }
  
}
