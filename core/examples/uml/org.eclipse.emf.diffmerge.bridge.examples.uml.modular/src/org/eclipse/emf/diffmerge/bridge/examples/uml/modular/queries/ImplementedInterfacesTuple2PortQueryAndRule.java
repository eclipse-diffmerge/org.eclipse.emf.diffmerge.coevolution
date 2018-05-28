/**
 * <copyright>
 * 
 * Copyright (c) 2015-2018 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries;

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.emf.diffmerge.bridge.util.structures.Tuple2;
import org.eclipse.emf.diffmerge.bridge.util.structures.Tuples;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.cs.DeployableElement;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.deployment.PartDeploymentLink;


/**
 * Implemented Interface -> Port.
 * @author Olivier Constant
 */
public class ImplementedInterfacesTuple2PortQueryAndRule
extends QueryAndRule<PartDeploymentLink, Tuple2<PhysicalComponent, Interface>, Port> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<Tuple2<PhysicalComponent, Interface>, Port> ID =
      new QueryAndRuleIdentifier<Tuple2<PhysicalComponent, Interface>, Port>("ImplementedInterface2Port"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public ImplementedInterfacesTuple2PortQueryAndRule(
      IQueryHolder<? extends PartDeploymentLink> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public Iterable<Tuple2<PhysicalComponent, Interface>> evaluate(
      PartDeploymentLink input_p, IQueryExecution queryExecution_p) {
    List<Tuple2<PhysicalComponent, Interface>> result = newIterable();
    DeployableElement deployed = input_p.getDeployedElement();
    if (deployed instanceof Part) {
      Type type = ((Part)deployed).getType(); //TODO: This variable should be reused from other queries
      if (type instanceof PhysicalComponent) {
        PhysicalComponent pc = (PhysicalComponent)type;
        for (Interface itf : pc.getImplementedInterfaces()) {
          result.add(Tuples.tuple(pc, itf));
        }
      }
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Port createTarget(Tuple2<PhysicalComponent, Interface> source_p, IQueryExecution queryExecution_p) {
    return UMLFactory.eINSTANCE.createPort();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(
      Tuple2<PhysicalComponent, Interface> source_p, Port target_p,
      IQueryExecution queryExecution_p, IMappingExecution mappingExecution_p) {
    // Name
    String name = source_p.get1().getName() + "_impl_" + source_p.get2().getName(); //$NON-NLS-1$
    target_p.setName(name);
    // Component
    PartDeploymentLink link = queryExecution_p.get(Deployment2ComponentQueryAndRule.ID);
    Component component = mappingExecution_p.get(link, Deployment2ComponentQueryAndRule.ID);
    component.getOwnedPorts().add(target_p);
  }
  
}
