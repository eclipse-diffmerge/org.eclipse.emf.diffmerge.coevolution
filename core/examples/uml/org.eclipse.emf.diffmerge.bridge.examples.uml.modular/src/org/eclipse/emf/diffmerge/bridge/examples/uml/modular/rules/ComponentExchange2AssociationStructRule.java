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
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule.QueryAndRuleIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.Rule;
import org.eclipse.emf.diffmerge.bridge.util.structures.Field;
import org.eclipse.emf.diffmerge.bridge.util.structures.IField;
import org.eclipse.emf.diffmerge.bridge.util.structures.IStruct;
import org.eclipse.emf.diffmerge.bridge.util.structures.Struct;
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
 * Component Exchange -> Association with Structs.
 * @author Olivier Constant
 */
public class ComponentExchange2AssociationStructRule extends Rule<ComponentExchange, IStruct> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<ComponentExchange, IStruct> ID =
      new QueryAndRuleIdentifier<ComponentExchange, IStruct>("ComponentExchange2AssociationStruct"); //$NON-NLS-1$
  
  /** The field for the association */
  public static final IField<Association> F_ASSOC =
      new Field<Association>("assoc"); //$NON-NLS-1$
  
  /** The field for the source property of the association */
  public static final IField<Property> F_SRC_PROPERTY =
      new Field<Property>("srcProperty"); //$NON-NLS-1$
  
  /** The field for the target property of the association */
  public static final IField<Property> F_TARGET_PROPERTY =
      new Field<Property>("targetProperty"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param provider_p a non-null object
   */
  public ComponentExchange2AssociationStructRule(
      IQuery<?, ? extends ComponentExchange> provider_p) {
    super(provider_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public IStruct createTarget(ComponentExchange source_p,
      IQueryExecution queryExecution_p) {
    Struct result = new Struct(F_ASSOC, F_SRC_PROPERTY, F_TARGET_PROPERTY);
    result.set(F_ASSOC, UMLFactory.eINSTANCE.createAssociation());
    result.set(F_SRC_PROPERTY, UMLFactory.eINSTANCE.createProperty());
    result.set(F_TARGET_PROPERTY, UMLFactory.eINSTANCE.createProperty());
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  public void defineTarget(ComponentExchange source_p,
      IStruct target_p, IQueryExecution queryEnv_p,
      IMappingExecution ruleEnv_p) {
    // Association
    target_p.get(F_ASSOC).setName("(Variant) " + source_p.getName()); //$NON-NLS-1$
    PhysicalArchitecture archi = (PhysicalArchitecture)source_p.eContainer().eContainer();
    Model container = ruleEnv_p.get(archi.getOwnedPhysicalComponent(), MainComponent2ModelQueryAndRule.ID);
    container.getOwnedTypes().add(target_p.get(F_ASSOC));
    // Source Property
    target_p.get(F_ASSOC).getNavigableOwnedEnds().add(target_p.get(F_SRC_PROPERTY));
    Component sourceType = ruleEnv_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getSource().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    target_p.get(F_SRC_PROPERTY).setType(sourceType);
    // Target Property
    target_p.get(F_ASSOC).getNavigableOwnedEnds().add(target_p.get(F_TARGET_PROPERTY));
    Component targetType = ruleEnv_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getTarget().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    target_p.get(F_TARGET_PROPERTY).setType(targetType);
  }
  
}
