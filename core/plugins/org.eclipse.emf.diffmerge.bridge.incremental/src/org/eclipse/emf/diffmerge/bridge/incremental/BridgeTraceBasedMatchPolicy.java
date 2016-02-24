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

import java.util.Comparator;

import org.eclipse.emf.diffmerge.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.api.scopes.IModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.ecore.EObject;


/**
 * A match policy which is based on bridge traces.
 * @author Olivier Constant
 */
public class BridgeTraceBasedMatchPolicy implements IMatchPolicy {
  
  /** The non-null newly-created scope */
  protected final IModelScope _createdScope;
  
  /** The non-null newly-created trace */
  protected final IBridgeTrace _createdTrace;
  
  /** The non-null existing trace */
  protected final IBridgeTrace _existingTrace;
  
  
  /**
   * Constructor
   * @param createdScope_p a non-null scope
   * @param createdTrace_p a non-null trace
   * @param existingTrace_p a non-null trace
   */
  public BridgeTraceBasedMatchPolicy(IModelScope createdScope_p, IBridgeTrace createdTrace_p,
      IBridgeTrace existingTrace_p) {
    _createdScope = createdScope_p;
    _createdTrace = createdTrace_p;
    _existingTrace = existingTrace_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.api.IMatchPolicy#getMatchID(org.eclipse.emf.ecore.EObject, org.eclipse.emf.diffmerge.api.scopes.IModelScope)
   */
  public Object getMatchID(EObject element_p, IModelScope scope_p) {
    IBridgeTrace trace = scope_p == _createdScope? _createdTrace: _existingTrace;
    Object result = trace.getCause(element_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.api.IMatchPolicy#getMatchIDComparator()
   */
  public Comparator<?> getMatchIDComparator() {
    return null; // Lower performance but works on non-comparable objects
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.api.IMatchPolicy#keepMatchIDs()
   */
  public boolean keepMatchIDs() {
    return false;
  }
  
}