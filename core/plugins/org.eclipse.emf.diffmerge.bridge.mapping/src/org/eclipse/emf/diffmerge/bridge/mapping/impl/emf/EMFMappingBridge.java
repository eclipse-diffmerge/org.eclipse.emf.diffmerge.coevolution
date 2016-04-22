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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.api.scopes.IFragmentedModelScope;
import org.eclipse.emf.diffmerge.api.scopes.IPersistentModelScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingBridge;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge;
import org.eclipse.emf.diffmerge.bridge.util.INormalizableModelScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;


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
   * Normalize the given persistent scope
   * @param scope_p a non-null scope
   */
   protected void normalizePersistentScope(IPersistentModelScope scope_p) {
    Collection<Resource> rootResources;
    if (scope_p instanceof IFragmentedModelScope)
      rootResources = ((IFragmentedModelScope)scope_p).getRootResources();
    else
      rootResources = Collections.singleton(scope_p.getHoldingResource());
    for (Resource rootResource : rootResources) {
      normalizeResource(rootResource);
    }
  }
  
  /**
   * Normalize the given resource by filtering out unnecessary roots
   * @param resource_p a non-null resource
   */
  protected void normalizeResource(Resource resource_p) {
    List<EObject> filtered = EcoreUtil.filterDescendants(resource_p.getContents());
    resource_p.getContents().retainAll(filtered);
  }
  
  /**
   * Normalize the given scope
   * @param scope_p a non-null scope
   */
  protected void normalizeScope(IEditableModelScope scope_p) {
    if (scope_p instanceof INormalizableModelScope) {
      ((INormalizableModelScope)scope_p).normalize();
    } else if (scope_p instanceof IPersistentModelScope) {
      normalizePersistentScope((IPersistentModelScope)scope_p);
    }
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.AbstractMappingBridge#targetsDefined(java.lang.Object)
   */
  @Override
  public void targetsDefined(TD targetSet_p) {
    super.targetsDefined(targetSet_p);
    normalizeScope(targetSet_p);
  }
  
}
