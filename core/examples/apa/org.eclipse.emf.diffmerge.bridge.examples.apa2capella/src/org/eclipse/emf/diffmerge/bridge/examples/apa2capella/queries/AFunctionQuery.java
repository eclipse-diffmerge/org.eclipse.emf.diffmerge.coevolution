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
package org.eclipse.emf.diffmerge.bridge.examples.apa2capella.queries;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.diffmerge.bridge.examples.apa.ABehavior;
import org.eclipse.emf.diffmerge.bridge.examples.apa.AFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryHolder;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.Query;

/**
 * @author Amine Lajmi
 *
 */
public class AFunctionQuery extends Query<ABehavior, AFunction>{

	/**
	 * @param parent_p (non-null)
	 */
	public AFunctionQuery(IQueryHolder<? extends ABehavior> parent_p) {
		super(parent_p);
	}

	/**
	 * 
	 * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery#evaluate(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
	 */
	public Iterator<AFunction> evaluate(ABehavior input_p, IQueryExecution environment_p) {
		EList<AFunction> functions = input_p.getFunctions();
		return functions.iterator();
	}
}