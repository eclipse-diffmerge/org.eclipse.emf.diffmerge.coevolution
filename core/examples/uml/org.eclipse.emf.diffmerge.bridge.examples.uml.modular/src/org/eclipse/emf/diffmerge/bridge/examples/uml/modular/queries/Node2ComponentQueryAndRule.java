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

import java.util.Collection;

import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;


/**
 * Node -> Component.
 * @author Olivier Constant
 */
public class Node2ComponentQueryAndRule extends QueryAndRule<PhysicalComponent, Part, Component> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<Part, Component> ID =
      new QueryAndRuleIdentifier<Part, Component>("Node2Component"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public Node2ComponentQueryAndRule(IQueryHolder<? extends PhysicalComponent> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public Iterable<Part> evaluate(PhysicalComponent input_p,
      IQueryExecution queryExecution_p) {
    Collection<Part> result = newIterable();
    for (Feature feature : input_p.getOwnedFeatures()) {
      if (feature instanceof TypedElement) {
        Type type = ((TypedElement) feature).getType();
        if (type instanceof PhysicalComponent &&
            ((PhysicalComponent)type).getNature() ==
            PhysicalComponentNature.NODE) {
          result.add((Part)feature);
        }
      }
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Component createTarget(Part source_p,
      IQueryExecution queryExecution_p) {
    return UMLFactory.eINSTANCE.createComponent();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(Part source_p,
      Component target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p) {
    // Name
    target_p.setName(source_p.getName());
    // Container
    Model container = mappingExecution_p.get(
        (PhysicalComponent)source_p.eContainer(), MainComponent2ModelQueryAndRule.ID);
    container.getOwnedTypes().add(target_p);
  }
  
}
