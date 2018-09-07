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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries;

import java.util.Collection;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.cs.DeployableElement;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.pa.deployment.PartDeploymentLink;


/**
 * Behavior Deployment -> Component.
 * @author Olivier Constant
 */
public class Deployment2ComponentQueryAndRule extends QueryAndRule<Part, PartDeploymentLink, Component> {

  /** The static identifier */
  public static final QueryAndRuleIdentifier<PartDeploymentLink, Component> ID =
      new QueryAndRuleIdentifier<PartDeploymentLink, Component>("Deployment2Component"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public Deployment2ComponentQueryAndRule(IQueryHolder<? extends Part> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Iterable<PartDeploymentLink> evaluate(Part input_p,
      IQueryExecution queryExecution_p) {
    return ((Collection)input_p.getOwnedDeploymentLinks());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Component createTarget(PartDeploymentLink source_p,
      IQueryExecution queryExecution_p) {
    return UMLFactory.eINSTANCE.createComponent();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(PartDeploymentLink source_p,
      Component target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p) {
    // Name
    DeployableElement deployable = source_p.getDeployedElement();
    Type type = ((Part)deployable).getType();
    target_p.setName(type.getName());
    // Container
    Part part = queryExecution_p.get(Node2ComponentQueryAndRule.ID);
    Component container = mappingExecution_p.get(part, Node2ComponentQueryAndRule.ID);
    container.getNestedClassifiers().add(target_p);
  }
  
}
