/*********************************************************************
 * Copyright (c) 2015-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
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
    GMFDiffPolicy diffPolicy = new GMFDiffPolicy();
    diffPolicy.setIgnoreOrders(true);
    EMFInteractiveBridge<PhysicalArchitecture, IEditableModelScope> result = 
        new EMFInteractiveBridge<PhysicalArchitecture, IEditableModelScope>(
            mappingBridge, diffPolicy, new UMLMergePolicy(), null);
    return result;
  }
  
}
