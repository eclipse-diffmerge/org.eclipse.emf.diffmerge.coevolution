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

import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.impl.emf.EMFSymbolFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.MappingCause;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;


/**
 * Functional Exchange -> Association.
 * @author Olivier Constant
 */
public class FunctionalExchange2AssociationRegisterQueryAndRule
extends QueryAndRule<AbstractFunction, FunctionalExchange, Association> {
  
  /** The static identifier */
  public static final QueryAndRuleIdentifier<FunctionalExchange, Association> ID =
      new QueryAndRuleIdentifier<FunctionalExchange, Association>("FunctionalExchange2Association"); //$NON-NLS-1$
  
  
  /**
   * Constructor
   * @param parent_p a non-null object
   */
  public FunctionalExchange2AssociationRegisterQueryAndRule(IQueryHolder<? extends AbstractFunction> parent_p) {
    super(parent_p, ID);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Iterable<FunctionalExchange> evaluate(
      AbstractFunction source_p, IQueryExecution queryExecution_p) {
    return (Collection)source_p.getOutgoing();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#createTarget(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  @Override
  public Association createTarget(FunctionalExchange source_p,
      IQueryExecution queryExecution_p) {
    return UMLFactory.eINSTANCE.createAssociation();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryAndRule#defineTarget(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution, org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution)
   */
  @Override
  public void defineTarget(FunctionalExchange source_p,
      Association target_p, IQueryExecution queryExecution_p,
      IMappingExecution mappingExecution_p) {
    // Name
    target_p.setName(source_p.getName());
    // Container
    PhysicalArchitecture archi = (PhysicalArchitecture)source_p.eContainer().eContainer().eContainer();
    Model container = mappingExecution_p.get(archi.getOwnedPhysicalComponent(), MainComponent2ModelQueryAndRule.ID);
    container.getOwnedTypes().add(target_p);
    // Create source Property
    Property srcEnd = UMLFactory.eINSTANCE.createProperty();
    target_p.getNavigableOwnedEnds().add(srcEnd);
    Component sourceType = mappingExecution_p.get(
        (AbstractFunction)source_p.getSource().eContainer(),
        Allocation2ComponentQueryAndRule.ID);
    srcEnd.setType(sourceType);
    // Create target Property
    Property dstEnd = UMLFactory.eINSTANCE.createProperty();
    target_p.getNavigableOwnedEnds().add(dstEnd);
    Component dstType = mappingExecution_p.get(
        (AbstractFunction)source_p.getTarget().eContainer(),
        Allocation2ComponentQueryAndRule.ID);
    dstEnd.setType(dstType);
    // REGISTRATION OF ADDITIONAL ELEMENTS
    registerInTrace(source_p, srcEnd, "_SRC", getMainRule(), mappingExecution_p); //$NON-NLS-1$
    registerInTrace(source_p, dstEnd, "_DST", getMainRule(), mappingExecution_p); //$NON-NLS-1$
  }
  
  /**
   * Register the given target element in the trace of the given rule environment
   * in order to support it in iterative mode, according to the given source element
   * and rule, and the given role the target element plays in the rule.
   * @param source_p a non-null element
   * @param target_p a non-null element
   * @param role_p a non-null, non-empty string
   * @param rule_p a non-null rule
   * @param execution_p a non-null bridge execution
   */
  public void registerInTrace(EObject source_p, EObject target_p, String role_p,
      IRule<?,?,?> rule_p, IBridgeExecution execution_p) {
    if (execution_p instanceof IBridgeExecution.Editable) {
      IBridgeTrace.Editable trace = ((IBridgeExecution.Editable)execution_p).getTrace();
      if (trace != null) {
        ISymbolFunction idFc = EMFSymbolFunction.getInstance();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Object causeSymbol = idFc.getSymbol(new MappingCause(null, source_p, rule_p));
        String discriminatedCauseSymbol = causeSymbol.toString() + role_p;
        trace.putCause(discriminatedCauseSymbol, target_p);
      }
    }
  }
  
}
