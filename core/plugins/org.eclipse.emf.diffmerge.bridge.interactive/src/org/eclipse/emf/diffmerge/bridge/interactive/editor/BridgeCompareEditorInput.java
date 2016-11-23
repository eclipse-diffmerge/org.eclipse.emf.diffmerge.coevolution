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
package org.eclipse.emf.diffmerge.bridge.interactive.editor;

import static org.eclipse.emf.diffmerge.bridge.incremental.EMFIncrementalBridge.TARGET_DATA_ROLE;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.diffmerge.api.IComparison;
import org.eclipse.emf.diffmerge.api.IMatch;
import org.eclipse.emf.diffmerge.api.diff.IElementPresence;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.incremental.IIncrementalBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.interactive.Messages;
import org.eclipse.emf.diffmerge.bridge.interactive.util.ResourceUtil;
import org.eclipse.emf.diffmerge.ui.EMFDiffMergeUIPlugin;
import org.eclipse.emf.diffmerge.ui.setup.EMFDiffMergeEditorInput;
import org.eclipse.emf.diffmerge.ui.viewers.AbstractComparisonViewer;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;


/**
 * A comparison editor input dedicated to bridges. The editor input is created
 * in memory once the user chooses to open the update viewer in a regular compare editor.
 * @author Amine Lajmi
 * @author Olivier Constant
 */
public class BridgeCompareEditorInput extends EMFDiffMergeEditorInput {
  
  /** The non-null bridge execution context */
  protected final IIncrementalBridgeExecution _execution;
  
  /** The non-null, potentially empty initial selection */
  protected final IStructuredSelection _initialSelection;
  
  
  /**
   * Default constructor
   * @param comparisonMethod_p a non-null comparison method
   * @param execution_p a non-null bridge execution context
   * @param initialSelection_p the non-null, potentially empty structured selection
   */
  public BridgeCompareEditorInput(BridgeComparisonMethod comparisonMethod_p,
      IIncrementalBridgeExecution execution_p,
      IStructuredSelection initialSelection_p) {
    super(comparisonMethod_p);
    _execution = execution_p;
    _initialSelection = initialSelection_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.setup.EMFDiffMergeEditorInput#contentsCreated()
   */
  @Override
  protected void contentsCreated() {
    super.contentsCreated();
    AbstractComparisonViewer viewer = getViewer();
    viewer.setSelection(_initialSelection, true);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.setup.EMFDiffMergeEditorInput#createTitle()
   */
  @Override
  protected String createTitle() {
    return Messages.InteractiveEMFBridge_DefaultDialogTitle;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.setup.EMFDiffMergeEditorInput#initializeCompareConfiguration()
   */
  @Override
  protected void initializeCompareConfiguration() {    
    CompareConfiguration cc = getCompareConfiguration();
    cc.setLeftEditable(getDiffNode().isEditable(true));
    cc.setRightEditable(getDiffNode().isEditable(false));
  }
  
  /**
   * Return the already-defined diff node
   * @return a non-null object
   */
  protected EMFDiffNode getDiffNode() {
    return ((BridgeComparisonMethod)_comparisonMethod).getDiffNode();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.ui.setup.EMFDiffMergeEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected Object prepareInput(IProgressMonitor monitor_p) throws
      InvocationTargetException, InterruptedException {
    if (monitor_p == null) // True when called from handleDispose()
      return null;
    String title = createTitle();
    setTitle(title);
    return getDiffNode();
  }
  
  /**
   * @see org.eclipse.compare.CompareEditorInput#saveChanges(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void saveChanges(IProgressMonitor monitor_p) throws CoreException {
    super.saveChanges(monitor_p);
    IComparison comparison = getDiffNode().getActualComparison();
    try {
      updateTrace(comparison, _execution.getTrace(), _execution.getReferenceTrace());
      if (getDiffNode().isModified(false)) {
        IBridgeTrace referenceTrace = _execution.getReferenceTrace();
        if (referenceTrace instanceof EObject) {
          Resource traceResource = ((EObject)referenceTrace).eResource();
          if (!_execution.isActuallyIncremental()) // Otherwise updateTrace(...) has done the job
            setTrace(traceResource, _execution.getTrace());
          if (!traceResource.getContents().isEmpty())
            ResourceUtil.makePersistent(traceResource);
          ResourceUtil.closeResource(traceResource);
        }
      }
    } catch (Exception e) {
      MessageDialog.openError(getShell(), EMFDiffMergeUIPlugin.LABEL,
          org.eclipse.emf.diffmerge.ui.Messages.ComparisonViewer_SaveFailed + e);
    }
  }

  /**
   * Set the given trace in the given trace resource
   * @param traceResource_p a non-null resource
   * @param trace_p a non-null trace
   */
  protected void setTrace(Resource traceResource_p, IBridgeTrace trace_p) {
    if (trace_p instanceof EObject) {
      traceResource_p.getContents().clear();
      traceResource_p.getContents().add((EObject)trace_p);
    }
  }
  
  /**
   * Update the traces with respect to the changes already merged from the editor
   * @param comparison_p a non-null comparison
   * @param createdTrace_p a non-null object
   * @param existingTrace_p a non-null object
   */
  protected void updateTrace(IComparison comparison_p,
      IBridgeTrace createdTrace_p, IBridgeTrace existingTrace_p) {
    if (existingTrace_p instanceof IBridgeTrace.Editable) {
      IBridgeTrace.Editable existingTrace = (IBridgeTrace.Editable)existingTrace_p;
      for (IMatch match : comparison_p.getMapping().getContents()) {
        // Added/removed elements
        IElementPresence presence = match.getElementPresenceDifference();
        if (presence != null && presence.isMerged()) {
          if (presence.getMergeDestination() == TARGET_DATA_ROLE) {
            if (presence.getPresenceRole() == TARGET_DATA_ROLE) {
              EObject removedTarget = presence.getElement();
              existingTrace.removeTarget(removedTarget);
            } else {
              // Addition
              EObject generated = presence.getElementMatch().get(TARGET_DATA_ROLE.opposite());
              Object cause = createdTrace_p.getCause(generated);
              if (cause != null) {
                EObject addedTarget = presence.getElementMatch().get(TARGET_DATA_ROLE);
                existingTrace.putCause(cause, addedTarget);
              }
            }
          }
        }
      }
    }
  }
  
}