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
package org.eclipse.emf.diffmerge.bridge.examples.apa2capella;

import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.examples.apa.AScope;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries.ABehaviorQuery;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries.AExchangeQuery;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries.AFunctionQuery;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries.ANodeQuery;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries.AScopeQuery;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.rules.ABehaviour2PhysicalComponentRule;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.rules.AExchange2FunctionalExchangeRule;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.rules.AFunction2PhysicalFunctionRule;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.rules.ANode2PhysicalComponentRule;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.emf.EMFMappingBridge;

/**
 * The APA to Capella transformation bridge.
 * 
 * @author Amine Lajmi
 *
 */
public class Apa2CapellaBridge extends EMFMappingBridge<AScope, IEditableModelScope> {

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unused")
	public Apa2CapellaBridge() {

		// AScope -> PhysicalSystem (PhysicalComponent | nature = UNSET)
		AScopeQuery scopeQuery = new AScopeQuery(this);

		// ANode -> PhysicalComponent (PhysicalComponent | nature = NODE)
		ANodeQuery nodeQuery = new ANodeQuery(scopeQuery);
		ANode2PhysicalComponentRule nodeRule = new ANode2PhysicalComponentRule(nodeQuery);

		// ABehavior -> PhysicalComponent (PhysicalComponent | nature = BEHAVIOR)
		ABehaviorQuery behaviorQuery = new ABehaviorQuery(nodeQuery);
		ABehaviour2PhysicalComponentRule behaviorRule = new ABehaviour2PhysicalComponentRule(behaviorQuery);

		// AFunction -> PhysicalFunction
		AFunctionQuery functionQuery = new AFunctionQuery(behaviorQuery);
		AFunction2PhysicalFunctionRule functionRule = new AFunction2PhysicalFunctionRule(functionQuery);

		// AExchange -> FunctionalExchange
		AExchangeQuery exchangeQuery = new AExchangeQuery(scopeQuery);
		AExchange2FunctionalExchangeRule exchangeRule = new AExchange2FunctionalExchangeRule(exchangeQuery);
	}
}