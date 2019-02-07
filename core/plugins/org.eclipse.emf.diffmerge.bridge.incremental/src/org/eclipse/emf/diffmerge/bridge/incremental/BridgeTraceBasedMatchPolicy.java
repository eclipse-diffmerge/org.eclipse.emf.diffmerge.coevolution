/*********************************************************************
 * Copyright (c) 2014-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.incremental;

import java.util.Comparator;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.generic.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.generic.api.scopes.ITreeDataScope;


/**
 * A match policy which is based on bridge traces.
 * @param <E> The type of data elements.
 * @author Olivier Constant
 */
public class BridgeTraceBasedMatchPolicy<E> implements IMatchPolicy<E> {
  
  /** The non-null newly-created scope */
  protected final ITreeDataScope<E> _createdScope;
  
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
  public BridgeTraceBasedMatchPolicy(ITreeDataScope<E> createdScope_p, IBridgeTrace createdTrace_p,
      IBridgeTrace existingTrace_p) {
    _createdScope = createdScope_p;
    _createdTrace = createdTrace_p;
    _existingTrace = existingTrace_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.generic.api.IMatchPolicy#getMatchID(java.lang.Object, org.eclipse.emf.diffmerge.generic.api.scopes.ITreeDataScope)
   */
  public Object getMatchID(E element_p, ITreeDataScope<E> scope_p) {
    IBridgeTrace trace = scope_p == _createdScope? _createdTrace: _existingTrace;
    Object result = trace.getCause(element_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.generic.api.IMatchPolicy#getMatchIDComparator()
   */
  public Comparator<?> getMatchIDComparator() {
    return null; // Lower performance but works on non-comparable objects
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.generic.api.IMatchPolicy#keepMatchIDs()
   */
  public boolean keepMatchIDs() {
    return false;
  }
  
}