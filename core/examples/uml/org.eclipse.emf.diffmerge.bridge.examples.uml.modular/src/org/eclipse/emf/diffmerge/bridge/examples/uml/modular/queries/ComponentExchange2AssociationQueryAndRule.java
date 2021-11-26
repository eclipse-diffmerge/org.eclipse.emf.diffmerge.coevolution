/*********************************************************************
 * Copyright (c) 2015-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries;

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.emf.diffmerge.bridge.util.structures.Tuple3;
import org.eclipse.emf.diffmerge.bridge.util.structures.Tuples;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.deployment.PartDeploymentLink;


/**
 * Component Exchange -> Association.
 * @author Olivier Constant
 */
public class ComponentExchange2AssociationQueryAndRule
extends QueryAndRule<PartDeploymentLink, ComponentExchange, Tuple3<Association, Property, Property>> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<ComponentExchange, Tuple3<Association, Property, Property>> ID =
      new QueryAndRuleIdentifier<ComponentExchange, Tuple3<Association, Property, Property>>(
          "ComponentExchange2Association"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public ComponentExchange2AssociationQueryAndRule(
      IQueryHolder<? extends PartDeploymentLink> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public Iterable<ComponentExchange> evaluate(PartDeploymentLink input_p,
      IQueryExecution queryExecution_p) {
    PhysicalComponent component =
        (PhysicalComponent)((Part)input_p.getDeployedElement()).getType();
    List<ComponentExchange> result = newIterable();
    for (Feature feature : component.getOwnedFeatures()) {
      if (feature instanceof ComponentPort)
        result.addAll(((ComponentPort)feature).getComponentExchanges());
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Tuple3<Association, Property, Property> createTarget(ComponentExchange source_p,
      IQueryExecution queryExecution_p) {
    return Tuples.tuple(
        UMLFactory.eINSTANCE.createAssociation(),
        UMLFactory.eINSTANCE.createProperty(),
        UMLFactory.eINSTANCE.createProperty());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(ComponentExchange source_p,
      Tuple3<Association, Property, Property> target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p) {
    // Association
    target_p.get1().setName(source_p.getName());
    PhysicalArchitecture archi = (PhysicalArchitecture)source_p.eContainer().eContainer();
    Model container = mappingExecution_p.get((PhysicalComponent)archi.getSystem(), MainComponent2ModelQueryAndRule.ID);
    container.getOwnedTypes().add(target_p.get1());
    // Source Property
    target_p.get1().getNavigableOwnedEnds().add(target_p.get2());
    Component sourceType = mappingExecution_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getSource().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    target_p.get2().setType(sourceType);
    // Target Property
    target_p.get1().getNavigableOwnedEnds().add(target_p.get3());
    Component targetType = mappingExecution_p.get((PartDeploymentLink)
        ((Part)((PhysicalComponent)source_p.getTarget().eContainer()).getTypedElements().get(0)).
        getDeployingLinks().get(0), Deployment2ComponentQueryAndRule.ID);
    target_p.get3().setType(targetType);
  }
  
}
