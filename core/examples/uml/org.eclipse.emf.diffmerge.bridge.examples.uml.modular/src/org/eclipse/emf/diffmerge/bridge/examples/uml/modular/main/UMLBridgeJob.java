/**
 * <copyright>
 * 
 * Copyright (c) 2015 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.main;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.Messages;
import org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob;
import org.eclipse.emf.diffmerge.bridge.interactive.EMFInteractiveBridge;
import org.eclipse.emf.diffmerge.bridge.uml.incremental.UMLMergePolicy;
import org.eclipse.emf.diffmerge.gmf.GMFDiffPolicy;
import org.eclipse.uml2.uml.UMLPackage;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;


/**
 * A job that turns Capella physical architectures into simple UML models.
 * @author O. CONSTANT
 */
public class UMLBridgeJob extends BridgeJob<PhysicalArchitecture> {
  
  /**
   * Constructor
   * @param context_p a non-null physical architecture
   */
  public UMLBridgeJob(PhysicalArchitecture context_p) {
    super(Messages.UMLBridgeJob_Name, context_p,
        context_p.eResource().getURI().trimFileExtension().appendFileExtension(UMLPackage.eNAME));
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob#getBridge()
   */
  @Override
  protected EMFInteractiveBridge<PhysicalArchitecture, IEditableModelScope> getBridge() {
    UMLBridge mappingBridge = new UMLBridge();
    // Make the mapping bridge incremental
    EMFInteractiveBridge<PhysicalArchitecture, IEditableModelScope> result = 
        new EMFInteractiveBridge<PhysicalArchitecture, IEditableModelScope>(
            mappingBridge, new GMFDiffPolicy(), new UMLMergePolicy(), null);
    return result;
  }
  
}
