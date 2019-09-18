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

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;


/**
 * Main Component -> Model.
 * @author Olivier Constant
 */
public class MainComponent2ModelQueryAndRule
extends QueryAndRule<PhysicalArchitecture, PhysicalComponent, Model> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<PhysicalComponent, Model> ID =
      new QueryAndRuleIdentifier<PhysicalComponent, Model>("PC2Model"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public MainComponent2ModelQueryAndRule(
      IQueryHolder<? extends PhysicalArchitecture> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public Iterable<PhysicalComponent> evaluate(PhysicalArchitecture input_p,
      IQueryExecution queryExecution_p) {
    return newIterable(input_p.getOwnedPhysicalComponent());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Model createTarget(PhysicalComponent source_p,
      IQueryExecution queryExecution_p) {
    return UMLFactory.eINSTANCE.createModel();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(PhysicalComponent source_p, Model target_p,
      IQueryExecution queryExecution_p, IMappingExecution mappingExecution_p) {
    // Add the target element as a root in the target scope
    IEditableModelScope targetScope = mappingExecution_p.getTargetDataSet();
    targetScope.add(target_p);
    // Name
    target_p.setName(source_p.getName());
  }
  
}
