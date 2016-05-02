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
package org.eclipse.emf.diffmerge.bridge.examples.apa2capella;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.diffmerge.impl.scopes.RootedModelScope;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

/**
 * A Model Scope which is nested inside a wider scope. Used in case the produced
 * target model is to be embedded into a host model.
 * 
 * @author Amine Lajmi
 *
 */
public class NestedModelScope extends RootedModelScope {

	/**
	 * The label of the already persisted target scope as it will appear in the
	 * diff/merge UI.
	 */
	private static final String TARGET_ORIGINATOR_LABEL = "EXISTING"; //$NON-NLS-1$

	/** The non-null physical system of the Capella model */
	private final PhysicalComponent _physicalSystem;

	/** The non-null root physical function of the Capella model */
	private final PhysicalFunction _rootPhysicalFunction;
	
	/** The relevant types for the roots of the scope */
	private static final Collection<EClass> RELEVANT_TYPES = Arrays.asList(
			CsPackage.eINSTANCE.getPart(),
			PaPackage.eINSTANCE.getPhysicalComponent(),
			PaPackage.eINSTANCE.getPhysicalFunction(),
			FaPackage.eINSTANCE.getFunctionalExchange());

	/**
	 * Constructs a model scope with the root elements in parameters.
	 * 
	 * @param physicalSystem_p a (non-null) physical system object
	 * @param rootPhysicalFunction_p a (non-null) physical function
	 */
	@SuppressWarnings("unchecked")
  public NestedModelScope(PhysicalComponent physicalSystem_p, PhysicalFunction rootPhysicalFunction_p) {
		super(getRelevantChildrenUnion(Arrays.asList(physicalSystem_p, rootPhysicalFunction_p)), true);
		_physicalSystem = physicalSystem_p;
		_rootPhysicalFunction = rootPhysicalFunction_p;
	}

	/**
	 * Adds the children of each container in containers_p as root elements of
	 * the target scope.
	 * 
	 * @param containers_p a (non-null, non-empty) list of containers objects
	 * @return A flat representation of containers children.
	 */
	protected static List<EObject> getRelevantChildrenUnion(Collection<? extends EObject> containers_p) {
		List<EObject> result = new ArrayList<EObject>();
		for (EObject container : containers_p) {
			for (EObject child : container.eContents()) {
				if (RELEVANT_TYPES.contains(child.eClass()))
					result.add(child);
			}
		}
		return result;
	}

	/**
	 * @see org.eclipse.emf.diffmerge.impl.scopes.RootedModelScope#add(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean add(final EObject element_p) {
		if (element_p instanceof Part) {
			_physicalSystem.getOwnedFeatures().add((Part) element_p);
		} else if (element_p instanceof PhysicalComponent) {
			_physicalSystem.getOwnedPhysicalComponents().add((PhysicalComponent) element_p);
		} else if (element_p instanceof PhysicalFunction) {
			_rootPhysicalFunction.getOwnedFunctions().add((PhysicalFunction) element_p);
		} else if (element_p instanceof FunctionalExchange) {
			_rootPhysicalFunction.getOwnedFunctionalExchanges().add((FunctionalExchange) element_p);
		}
		return super.add(element_p);
	}

	/**
	 * @see org.eclipse.emf.diffmerge.impl.scopes.AbstractModelScope#getOriginator()
	 */
	@Override
	public Object getOriginator() {
		return TARGET_ORIGINATOR_LABEL;
	}
}