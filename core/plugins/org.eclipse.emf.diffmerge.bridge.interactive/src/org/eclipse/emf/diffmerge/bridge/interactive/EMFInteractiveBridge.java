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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.api.IDiffPolicy;
import org.eclipse.emf.diffmerge.api.IMergePolicy;
import org.eclipse.emf.diffmerge.api.IMergeSelector;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge;
import org.eclipse.emf.diffmerge.diffdata.EComparison;
import org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog;
import org.eclipse.emf.diffmerge.ui.viewers.AbstractComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.ComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


/**
 * An implementation of IIncrementalBridge for EMF data sets based on a trace model and
 * the EMF Diff/Merge engine. Incrementality (updates) is realized via a merge operation
 * which is composed of an automatic phase followed by an optional interactive phase.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IIncrementalBridge
 * @author Olivier Constant
 */
public class EMFInteractiveBridge<SD, TD extends IEditableModelScope>
extends EMFIncrementalBridge<SD, TD> {
  
  /**
   * Constructor
   * @param bridge_p a non-null bridge acting as the non-incremental bridge
   * @param diffPolicy_p an optional diff policy for updates (null for default)
   * @param mergePolicy_p an optional merge policy for updates (null for default)
   * @param merger_p an optional merge selector for non-interactive updates
   *        (null for fully interactive) where (TARGET_DATA_ROLE: existing scope,
   *        TARGET_DATA_ROLE.opposite(): created scope)
   */
  public EMFInteractiveBridge(IBridge<SD, TD> bridge_p, IDiffPolicy diffPolicy_p,
      IMergePolicy mergePolicy_p, IMergeSelector merger_p) {
    super(bridge_p, diffPolicy_p, mergePolicy_p, merger_p);
  }
  
  /**
   * Create and return the comparison viewer for interactive merge in the given composite
   * @param parent_p a non-null composite
   * @return a non-null viewer
   */
  protected AbstractComparisonViewer createComparisonViewer(Composite parent_p) {
    return new ComparisonViewer(parent_p);
  }
  
  /**
   * Create and return a diff node for the interactive merge
   * @param comparison_p a non-null comparison
   * @param domain_p an optional editing domain 
   * @return a non-null object
   */
  protected EMFDiffNode createDiffNode(EComparison comparison_p,
      EditingDomain domain_p) {
    final EMFDiffNode diffNode = new EMFDiffNode(comparison_p, domain_p, true, false);
    diffNode.setDefaultShowImpact(false);
    diffNode.setReferenceRole(TARGET_DATA_ROLE);
    return diffNode;
  }
  
  /**
   * Create and return a dialog for interactive merge on the given diff node
   * @param diffNode_p a non-null diff node
   * @return a non-null window
   */
  protected Window createMergeDialog(EMFDiffNode diffNode_p) {
    return new DiffMergeDialog(
        Display.getDefault().getActiveShell(), getTitle(), diffNode_p) {
      /**
       * @see org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog#createComparisonViewer(org.eclipse.swt.widgets.Composite)
       */
      @Override
      protected AbstractComparisonViewer createComparisonViewer(
          Composite parent_p) {
        return EMFInteractiveBridge.this.createComparisonViewer(parent_p);
      }
    };
  }
  
  /**
   * Return the title of the interactive dialog
   * @return a non-null string
   */
  protected String getTitle() {
    return Messages.InteractiveEMFBridge_DefaultDialogTitle;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge#isAlwaysInteractive()
   */
  @Override
  protected boolean isAlwaysInteractive() {
    return true;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge#mergeInteractively(org.eclipse.emf.diffmerge.diffdata.EComparison, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus mergeInteractively(EComparison comparison_p, IProgressMonitor monitor_p) {
    EditingDomain domain = null;
    IEditableModelScope targetScope = comparison_p.getTargetScope();
    if (targetScope.getAllContents().hasNext())
      domain = AdapterFactoryEditingDomain.getEditingDomainFor(targetScope.getAllContents().next());
    final EMFDiffNode diffNode = createDiffNode(comparison_p, domain);
    final int[] done = new int[] {0};
    final Display display = Display.getDefault();
    display.syncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        Window dialog = createMergeDialog(diffNode);
        done[0] = dialog.open();
      }
    });
    IStatus result = Window.OK == done[0]? Status.OK_STATUS: Status.CANCEL_STATUS;
    return result;
  }
  
}
