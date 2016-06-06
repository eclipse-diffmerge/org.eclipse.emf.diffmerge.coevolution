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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.rules;

import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.Deployment2ComponentQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries.MainComponent2ModelQueryAndRule;
import org.eclipse.emf.diffmerge.bridge.examples.uml.modular.rules.Constants.AssocElements;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule.QueryAndRuleIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.Rule;
import org.eclipse.emf.diffmerge.bridge.util.structures.EnumStruct;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.deployment.PartDeploymentLink;


/**
 * Component Exchange -> Association with Enum Structs.
 * @author Olivier Constant
 */
public class ComponentExchange2AssociationEnumStructRule
extends Rule<ComponentExchange, EnumStruct<AssocElements>> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<ComponentExchange, EnumStruct<AssocElements>> ID =
      new QueryAndRuleIdentifier<ComponentExchange, EnumStruct<AssocElements>>(
          "ComponentExchange2AssociationEnumStruct"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param provider_p a non-null object
   */
  public ComponentExchange2AssociationEnumStructRule(
      IQuery<?, ? extends ComponentExchange> provider_p) {
    super(provider_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public EnumStruct<AssocElements> createTarget(ComponentExchange source_p,
      IQueryExecution queryExecution_p) {
    EnumStruct<AssocElements> result = new EnumStruct<AssocElements>(AssocElements.class);
    result.set(AssocElements.ASSOC, UMLFactory.eINSTANCE.createAssociation());
    result.set(AssocElements.SRC_PROP, UMLFactory.eINSTANCE.createProperty());
    result.set(AssocElements.TARGET_PROP, UMLFactory.eINSTANCE.createProperty());
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  public void defineTarget(ComponentExchange source_p,
      EnumStruct<AssocElements> target_p, IQueryExecution queryExecution_p,
      IMappingExecution ruleEnv_p) {
    // Association
    Association assoc = target_p.get(AssocElements.ASSOC);
    assoc.setName("(Variant 2) " + source_p.getName()); //$NON-NLS-1$
    PhysicalArchitecture archi = (PhysicalArchitecture)source_p.eContainer().eContainer();
    Model container = ruleEnv_p.get(
        archi.getOwnedPhysicalComponent(), MainComponent2ModelQueryAndRule.ID);
    container.getOwnedTypes().add(assoc);
    // Source Property
    Property srcProperty = target_p.get(AssocElements.SRC_PROP);
    assoc.getNavigableOwnedEnds().add(srcProperty);
    Component sourceType = ruleEnv_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getSource().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    srcProperty.setType(sourceType);
    // Target Property
    Property targetProperty = target_p.get(AssocElements.TARGET_PROP);
    assoc.getNavigableOwnedEnds().add(targetProperty);
    Component targetType = ruleEnv_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getTarget().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    targetProperty.setType(targetType);
  }
  
}
