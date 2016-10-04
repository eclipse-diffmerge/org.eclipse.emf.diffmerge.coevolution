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
package org.eclipse.emf.diffmerge.bridge.interactive;

import org.eclipse.emf.diffmerge.api.Role;
import org.eclipse.emf.diffmerge.api.scopes.IModelScope;
import org.eclipse.emf.diffmerge.ui.viewers.DirectedComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.emf.diffmerge.ui.viewers.EnhancedComparisonSideViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;


/**
 * A comparison viewer that lets the user check and possibly manually control
 * the update phase during the execution of an interactive, incremental bridge.
 * @author Olivier Constant
 */
public class BridgeComparisonViewer extends DirectedComparisonViewer {
  
  /**
   * Constructor
   * @param parent_p a non-null composite
   */
  public BridgeComparisonViewer(Composite parent_p) {
    this(parent_p, null);
  }
  
  /**
   * Constructor
   * @param parent_p a non-null composite
   * @param actionBars_p optional action bars
   */
  public BridgeComparisonViewer(Composite parent_p, IActionBars actionBars_p) {
    super(parent_p, actionBars_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.viewers.ComparisonViewer#doCreateViewerSynthesisSide(org.eclipse.swt.widgets.Composite, boolean)
   */
  @Override
  protected EnhancedComparisonSideViewer doCreateViewerSynthesisSide(
      Composite parent_p, boolean isLeftSide_p) {
    return new EnhancedBridgeComparisonSideViewer(parent_p, isLeftSide_p);
  }
  
  
  /**
   * A viewer for the model on a given side in the synthesis row of the GUI.
   */
  protected static class EnhancedBridgeComparisonSideViewer extends EnhancedComparisonSideViewer {
    /**
     * Constructor
     * @param parent_p a non-null composite
     * @param isLeftSide_p whether the side is left or right
     */
    public EnhancedBridgeComparisonSideViewer(Composite parent_p, boolean isLeftSide_p) {
      super(parent_p, isLeftSide_p);
    }
    /**
     * Return a label for the reference side
     * @return a non-null string
     */
    protected String getReferenceSideText() {
      return Messages.BridgeComparisonViewer_ReferenceLabel;
    }
    /**
     * Return a label for the target side
     * @return a non-null string
     */
    protected String getTargetSideText() {
      return Messages.BridgeComparisonViewer_TargetLabel;
    }
    /**
     * @see org.eclipse.emf.diffmerge.ui.viewers.EnhancedComparisonSideViewer#updateHeaderImage(org.eclipse.swt.widgets.Label, org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode, org.eclipse.emf.diffmerge.api.scopes.IModelScope)
     */
    @Override
    protected void updateHeaderImage(Label headerImageWidget_p,
        EMFDiffNode input_p, IModelScope scope_p) {
      Image image = null;
      // Always use the default image for the TARGET side
      if (input_p != null) {
        IModelScope referenceScope = input_p.getActualComparison().getTargetScope();
        image = getHeaderLabelProvider().getImage(referenceScope);
      }
      headerImageWidget_p.setImage(image);
    }
    /**
     * @see org.eclipse.emf.diffmerge.ui.viewers.EnhancedComparisonSideViewer#updateHeaderText(org.eclipse.swt.widgets.Label, org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode, org.eclipse.emf.diffmerge.api.scopes.IModelScope)
     */
    @Override
    protected void updateHeaderText(Label headerTextWidget_p,
        EMFDiffNode input_p, IModelScope scope_p) {
      String text = null;
      if (input_p != null) {
        Role leftRole = input_p.getRoleForSide(true);
        boolean isTarget = _isLeftSide == (leftRole == Role.TARGET); 
        text = isTarget? getTargetSideText(): getReferenceSideText();
      }
      headerTextWidget_p.setText(text);
      headerTextWidget_p.setToolTipText(text);
    }
  }
  
}
