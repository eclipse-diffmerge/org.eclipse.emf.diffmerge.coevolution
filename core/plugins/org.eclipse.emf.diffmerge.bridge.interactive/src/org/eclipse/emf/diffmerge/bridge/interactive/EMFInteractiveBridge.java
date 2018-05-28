/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.api.IDiffPolicy;
import org.eclipse.emf.diffmerge.api.IMergePolicy;
import org.eclipse.emf.diffmerge.api.IMergeSelector;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge;
import org.eclipse.emf.diffmerge.bridge.interactive.editor.BridgeCompareEditorInput;
import org.eclipse.emf.diffmerge.bridge.interactive.editor.BridgeComparisonMethod;
import org.eclipse.emf.diffmerge.diffdata.EComparison;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
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
  
  /** The status message for a switch to editor mode in the interactive phase */
  public static final String STATUS_SWITCH_TO_EDITOR = "editorSwitch"; //$NON-NLS-1$
  
  
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
    diffNode.setDrivingRole(TARGET_DATA_ROLE);
    diffNode.setLeftRole(TARGET_DATA_ROLE.opposite()); // Left to right
    return diffNode;
  }
  
  /**
   * Create and return a dialog for interactive merge on the given diff node
   * @param diffNode_p a non-null diff node
   * @return a non-null window
   */
  protected UpdateDialog createMergeDialog(EMFDiffNode diffNode_p) {
    return new UpdateDialog(Display.getDefault().getActiveShell(), getTitle(), diffNode_p);
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
   * @see org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge#handleInteractiveMerge(org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus handleInteractiveMerge(IIncrementalBridgeExecution execution_p, IProgressMonitor monitor_p) {
    IStatus result = Status.CANCEL_STATUS;
    EditingDomain domain = null;
    Object mergeData = execution_p.getInteractiveMergeData();
    if (mergeData instanceof EComparison) {
      EComparison comparison = (EComparison)mergeData;
      IEditableModelScope targetScope = comparison.getTargetScope();
      if (targetScope.getAllContents().hasNext())
        domain = AdapterFactoryEditingDomain.getEditingDomainFor(
            targetScope.getAllContents().next());
      final EMFDiffNode diffNode = createDiffNode(comparison, domain);
      final int[] returnCodeWrapper = new int[] {0};
      final IStructuredSelection[] selectionWrapper = new IStructuredSelection[] {null};
      final Display display = Display.getDefault();
      display.syncExec(new Runnable() {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
          UpdateDialog dialog = createMergeDialog(diffNode);
          returnCodeWrapper[0] = dialog.open();
          selectionWrapper[0] = dialog.getSelection();
        }
      });
      switch (returnCodeWrapper[0]) {
      case IDialogConstants.OK_ID:
        result = Status.OK_STATUS;
        break;
      case UpdateDialog.OPEN_EDITOR_ID:
        openInEditor(execution_p, diffNode, selectionWrapper[0]);
        result = new Status(
            IStatus.INFO, InteractiveBridgePlugin.getDefault().getPluginId(),
            STATUS_SWITCH_TO_EDITOR);
        break;
      default:
        result = Status.CANCEL_STATUS;
      }
    }
    return result;
  }
  
  /**
   * Open the given bridge execution and given corresponding diff node in an editor
   * @param execution_p a non-null bridge execution
   * @param diffNode_p a non-null diff node
   * @param initialSelection_p a non-null, potentially empty initial selection
   */
  protected void openInEditor(IIncrementalBridgeExecution execution_p,
      EMFDiffNode diffNode_p, IStructuredSelection initialSelection_p) {
    BridgeComparisonMethod method = new BridgeComparisonMethod(diffNode_p);
    BridgeCompareEditorInput editorInput =
        new BridgeCompareEditorInput(method, execution_p, initialSelection_p);
    CompareUI.openCompareEditor(editorInput, true);
  }
  
}