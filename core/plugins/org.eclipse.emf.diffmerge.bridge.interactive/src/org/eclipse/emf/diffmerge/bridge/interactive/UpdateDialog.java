/**
 * <copyright>
 * 
 * Copyright (c) 2016 Thales Global Services S.A.S.
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

import org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog;
import org.eclipse.emf.diffmerge.ui.viewers.AbstractComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;


/**
 * A dialog dedicated to interactive updates during bridge executions.
 * @author Olivier Constant
 */
public class UpdateDialog extends DiffMergeDialog {
  
  /** The ID of the 'defer' button */
  public static final int DEFER_ID = 2;
  
  /** The ID of the 'open editor' button */
  public static final int OPEN_EDITOR_ID = 3;
  
  
  /**
   * Constructor
   * @param shell_p a non-null shell
   * @param title_p an optional title for the dialog
   * @param input_p a non-null input for the dialog
   */
  public UpdateDialog(Shell shell_p, String title_p, EMFDiffNode input_p) {
    super(shell_p, title_p, input_p);
  }
  
  /**
   * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
   */
  @Override
  protected void buttonPressed(int buttonId_p) {
    if (DEFER_ID == buttonId_p || OPEN_EDITOR_ID == buttonId_p) {
      setReturnCode(buttonId_p);
      close();
    } else {
      super.buttonPressed(buttonId_p);
    }
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent_p) {
//    createOpenEditorButton(parent_p);
    if (isEditable())
      createDeferButton(parent_p);
    super.createButtonsForButtonBar(parent_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog#createComparisonViewer(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected AbstractComparisonViewer createComparisonViewer(Composite parent_p) {
    return new UpdateViewer(parent_p);
  }
  
  /**
   * Create a 'defer' button
   * @param parent_p a non-null composite
   */
  protected Button createDeferButton(Composite parent_p) {
    Button result = createButton(
        parent_p, DEFER_ID, Messages.UpdateDialog_Defer, false);
    result.setEnabled(_input.getActualComparison().hasRemainingDifferences());
    return result;
  }
  
//  /**
//   * Create an 'open editor' button
//   * @param parent_p a non-null composite
//   */
//  protected Button createOpenEditorButton(Composite parent_p) {
//    Button result = createButton(
//        parent_p, OPEN_EDITOR_ID, "Open Editor", false);
//    result.setEnabled(_input.getActualComparison().hasRemainingDifferences());
//    return result;
//  }
  
}
