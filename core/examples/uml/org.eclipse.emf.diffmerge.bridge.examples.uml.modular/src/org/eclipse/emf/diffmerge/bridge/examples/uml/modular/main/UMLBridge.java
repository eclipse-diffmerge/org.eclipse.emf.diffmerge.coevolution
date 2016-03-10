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
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.Allocation2ComponentQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.ComponentExchange2AssociationQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.Deployment2ComponentQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.FunctionalExchange2AssociationRegisterQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.ImplementedInterfacesTuple2PortQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.MainComponent2ModelQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.Node2ComponentQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.emf.EMFMappingBridge;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;


/**
 * The simple mapping bridge between Capella and UML.
 */
public class UMLBridge extends EMFMappingBridge<PhysicalArchitecture, IEditableModelScope> {
  
  /**
   * Constructor
   */
  @SuppressWarnings("unused")
  public UMLBridge() {
    // Main component -> Model
    MainComponent2ModelQueryAndRule mainQR = new MainComponent2ModelQueryAndRule(this);
    // Node -> Component
    Node2ComponentQueryAndRule nodeQR = new Node2ComponentQueryAndRule(mainQR);
    // Behavior Deployment -> Component
    Deployment2ComponentQueryAndRule deploymentQR = new Deployment2ComponentQueryAndRule(nodeQR);
    // Component Exchange -> Association
    ComponentExchange2AssociationQueryAndRule ceQR =
        new ComponentExchange2AssociationQueryAndRule(deploymentQR);
    // Component Exchange -> Association with Structs
//    ComponentExchange2AssociationStructRule ceStructQR =
//        new ComponentExchange2AssociationStructRule(ceQR);
    // Component Exchange -> Association with Enum Structs
//    ComponentExchange2AssociationEnumStructRule ceEnumStructQR =
//        new ComponentExchange2AssociationEnumStructRule(ceQR);
    // Implemented Interface -> Port
    ImplementedInterfacesTuple2PortQueryAndRule itfQR =
        new ImplementedInterfacesTuple2PortQueryAndRule(deploymentQR);
    // Function Allocation -> Component
    Allocation2ComponentQueryAndRule allocationQR = new Allocation2ComponentQueryAndRule(deploymentQR);
    // Functional Exchange -> Association
    FunctionalExchange2AssociationRegisterQueryAndRule functionalExchangeQR =
        new FunctionalExchange2AssociationRegisterQueryAndRule(allocationQR);
  }
  
}
