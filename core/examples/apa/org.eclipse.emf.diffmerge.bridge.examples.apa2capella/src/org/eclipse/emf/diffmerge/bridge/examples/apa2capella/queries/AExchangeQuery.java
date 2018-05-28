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
package org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries;

import org.eclipse.emf.diffmerge.bridge.examples.apa.AExchange;
import org.eclipse.emf.diffmerge.bridge.examples.apa.AScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.Query;


/**
 * @author Amine Lajmi
 *
 */
public class AExchangeQuery extends Query<AScope, AExchange>{

	/**
	 * @param parent_p (non-null)
	 */
	public AExchangeQuery(IQueryHolder<? extends AScope> parent_p) {
		super(parent_p);
	}

	/**
	 * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
	 */
	public Iterable<AExchange> evaluate(AScope input_p, IQueryExecution environment_p) {
		return input_p.getExchanges();
	}
}