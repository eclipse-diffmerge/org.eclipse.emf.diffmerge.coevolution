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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.queries;

import java.util.Iterator;
import java.util.List;

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
  public Iterator<PartDeploymentLink> evaluate(Part input_p,
      IQueryExecution queryExecution_p) {
    return ((List)input_p.getOwnedDeploymentLinks()).iterator();
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
