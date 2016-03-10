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
package org.eclipse.emf.diffmerge.bridge.mapping.impl.emf;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingBridge;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge;
import org.eclipse.emf.diffmerge.bridge.util.INormalizableModelScope;
import org.eclipse.emf.ecore.EObject;


/**
 * An implementation of IMappingBridge to EMF data sets defined as IEditableModelScope.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IMappingBridge
 * @author Olivier Constant
 */
public class EMFMappingBridge<SD, TD extends IEditableModelScope> extends AbstractMappingBridge<SD, TD> {
  
  /** The number of rules, considered constant */
  private int _nbRules;
  
  /** The number of queries, considered constant */
  private int _nbQueries;
  
  
  /**
   * Constructor
   */
  public EMFMappingBridge() {
    super();
    _nbQueries = -1;
    _nbRules = -1;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge#addElementaryTarget(java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean addElementaryTarget(TD targetScope_p, Object target_p) {
    boolean result = false;
    if (target_p instanceof EObject)
      result = targetScope_p.add((EObject)target_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge#getNbQueries()
   */
  @Override
  public int getNbQueries() {
    if (_nbQueries < 0)
      _nbQueries = super.getNbQueries();
    return _nbQueries;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge#getNbRules()
   */
  @Override
  public int getNbRules() {
    if (_nbRules < 0)
      _nbRules = super.getNbRules();
    return _nbRules;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge#targetsDefined(java.lang.Object)
   */
  @Override
  public void targetsDefined(TD targetSet_p) {
    super.targetsDefined(targetSet_p);
    if (targetSet_p instanceof INormalizableModelScope)
      ((INormalizableModelScope)targetSet_p).normalize();
  }
  
}
