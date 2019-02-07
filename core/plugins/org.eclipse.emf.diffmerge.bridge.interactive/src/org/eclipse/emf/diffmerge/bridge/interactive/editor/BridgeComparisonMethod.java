/*********************************************************************
 * Copyright (c) 2016-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.interactive.editor;

import org.eclipse.emf.diffmerge.bridge.interactive.UpdateViewer;
import org.eclipse.emf.diffmerge.diffdata.impl.EComparisonImpl;
import org.eclipse.emf.diffmerge.generic.api.IDiffPolicy;
import org.eclipse.emf.diffmerge.generic.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.generic.api.IMergePolicy;
import org.eclipse.emf.diffmerge.generic.api.Role;
import org.eclipse.emf.diffmerge.generic.api.scopes.IEditableTreeDataScope;
import org.eclipse.emf.diffmerge.generic.gdiffdata.GComparison;
import org.eclipse.emf.diffmerge.ui.specification.IModelScopeDefinition;
import org.eclipse.emf.diffmerge.ui.specification.ext.AbstractComparisonMethod;
import org.eclipse.emf.diffmerge.ui.viewers.AbstractComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;


/**
 * A comparison method dedicated to the interactive phase of bridge execution
 * when done in an editor. It is based on an already-existing diff node.
 * @author Amine Lajmi
 * @author Olivier Constant
 */
public class BridgeComparisonMethod extends AbstractComparisonMethod<EObject> {
  
  /** The non-null diff node */
  private final EMFDiffNode _diffNode;
  
  
  /**
   * Constructor
   * @param diffNode_p the existing non-null diff node
   */
  public BridgeComparisonMethod(EMFDiffNode diffNode_p) {
    _diffNode = diffNode_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#createComparison(org.eclipse.emf.diffmerge.generic.api.scopes.IEditableTreeDataScope, org.eclipse.emf.diffmerge.generic.api.scopes.IEditableTreeDataScope, org.eclipse.emf.diffmerge.generic.api.scopes.IEditableTreeDataScope)
   */
  public GComparison<EObject, ?, ?> createComparison(
      IEditableTreeDataScope<EObject> targetScope_p,
      IEditableTreeDataScope<EObject> referenceScope_p,
      IEditableTreeDataScope<EObject> ancestorScope_p) {
    return new EComparisonImpl(targetScope_p, referenceScope_p, ancestorScope_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.ext.AbstractComparisonMethod#createComparisonViewer(org.eclipse.swt.widgets.Composite, org.eclipse.ui.IActionBars)
   */
  @Override
  public AbstractComparisonViewer createComparisonViewer(Composite parent_p,
      IActionBars actionBars_p) {
    return new UpdateViewer(parent_p, actionBars_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.ext.AbstractComparisonMethod#createEditingDomain()
   */
  @Override
  public EditingDomain createEditingDomain() {
    return getDiffNode().getEditingDomain();
  }
  
  /**
   * Return the diff node of this comparison method
   * @return a non-null object
   */
  public EMFDiffNode getDiffNode() {
    return _diffNode;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#getDiffPolicy()
   */
  @SuppressWarnings("unchecked")
  public IDiffPolicy<EObject> getDiffPolicy() {
    return getDiffNode().getActualComparison().getLastDiffPolicy();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#getMatchPolicy()
   */
  @SuppressWarnings("unchecked")
  public IMatchPolicy<EObject> getMatchPolicy() {
    return getDiffNode().getActualComparison().getLastMatchPolicy();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#getMergePolicy()
   */
  @SuppressWarnings("unchecked")
  public IMergePolicy<EObject> getMergePolicy() {
    return getDiffNode().getActualComparison().getLastMergePolicy();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#getModelScopeDefinition(org.eclipse.emf.diffmerge.generic.api.Role)
   */
  public IModelScopeDefinition getModelScopeDefinition(Role role_p) {
    return null; // No scope definitions since the diff node is already defined
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#getTwoWayReferenceRole()
   */
  public Role getTwoWayReferenceRole() {
    return getDiffNode().getReferenceRole();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#setTwoWayReferenceRole(org.eclipse.emf.diffmerge.generic.api.Role)
   */
  public void setTwoWayReferenceRole(Role role_p) {
     getDiffNode().setReferenceRole(role_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod#swapScopeDefinitions(org.eclipse.emf.diffmerge.generic.api.Role, org.eclipse.emf.diffmerge.generic.api.Role)
   */
  public boolean swapScopeDefinitions(Role role1_p, Role role2_p) {
    return false;
  }
  
}