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
package org.eclipse.emf.diffmerge.bridge.log4j;

import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IHyperlink;

/**
 * A hyper link displayed in the console which points to a model element
 */
public class BridgeElementLink implements IHyperlink {
 
  /**
   * The logger for this class
   */
  static final Logger logger = Logger.getLogger(BridgeElementLink.class);
  /**
   * The URI backed by this element link
   */
  private final URI _uri;

  /**
   * Default constructor
   * 
   * @param uri_p (non-null) URI with fragment 
   */
  public BridgeElementLink(URI uri_p) {
    this._uri = normalize(uri_p);
  }
  
  /**
   * Normalize the URI: handle specific cases, e.g. bridges which target Capella
   * models create a temporary model in memory, which is then compared to the
   * existing target model during the matching phase. The in-memory model has
   * the same URI with the existing model except the scheme which is by default
   * set to "dummy". See
   * {@link org.eclipse.emf.diffmerge.bridge.capella.integration.CapellaBridgeJob#createEmptyProject(IEditableModelScope)}
   * 
   * @param uri_p
   *          (non-null) uri with fragment
   * @return the normalized uri.
   */
  private URI normalize(URI uri_p) {
    if (uri_p.scheme().equals("dummy")) { //$NON-NLS-1$
      //create platform resource URI to be able to point to the object in the real target model.
      return URI.createPlatformResourceURI(uri_p.path(), true).appendFragment(uri_p.fragment());
    }
    return uri_p;
  }
  
  /**
   * @see org.eclipse.ui.console.IHyperlink#linkEntered()
   */
  public void linkEntered() {
    //do nothing
  }

  /**
   * @see org.eclipse.ui.console.IHyperlink#linkExited()
   */
  public void linkExited() {
    //do nothing
  }

  /**
   * @see org.eclipse.ui.console.IHyperlink#linkActivated()
   */
  public void linkActivated() {
    if (_uri.fragment() != null) {
      String fileName = _uri.trimFragment().lastSegment();
      try {
        IEditorPart editor = openInEditor(fileName);
        if (editor!=null) {
          EditingDomain editingDomain = ((IEditingDomainProvider) editor).getEditingDomain();
          ResourceSet internalResourceSet = editingDomain.getResourceSet();
          EObject internalObject = internalResourceSet.getEObject(_uri, false);
          final Collection<?> selection = Collections.singletonList(internalObject);
          if (selection != null && !selection.isEmpty()) {
            final Viewer viewer = ((IViewerProvider) editor).getViewer();
            Runnable runnable = new Runnable() {
              public void run() {
                if (viewer != null)
                  viewer.setSelection(new StructuredSelection(selection.toArray()), true);
              }
            };
            editor.getSite().getShell().getDisplay().asyncExec(runnable);
          }
          //bring the editor up front
          IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
          IWorkbenchPage page = workbenchWindow.getActivePage();
          page.activate(editor); 
        }
      } catch (PartInitException ex) {
        logger.error(ex.getStackTrace(), ex);
      }
    }
  }
  
  /**
   * Open the default EMF editor for the file name given as input.
   * 
   * @param fileName_p the file name to open the editor on
   * @return the (possibly-null) editor opened on the file name given as input
   * @throws PartInitException
   */
  public IEditorPart openInEditor(String fileName_p) throws PartInitException {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    IWorkbenchPage page = workbenchWindow.getActivePage();
    IEditorDescriptor editorDescriptor = EditUIUtil.getDefaultEditor(fileName_p);
    URIEditorInput editorInput = new URIEditorInput(_uri.trimFragment());
    // 1. look if editor is already open
    IEditorPart editor = page.findEditor(editorInput);
    if (editor == null) {
      // 2. no opened editor, open new one.
      editor = page.openEditor(editorInput, editorDescriptor.getId());
    }
    return editor;
  }
}