/**
 * <copyright>
 * 
 * Copyright (c) 2014 Thales Global Services S.A.S.
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.diffmerge.api.scopes.IEditableModelScope;
import org.eclipse.emf.diffmerge.bridge.examples.apa.AScope;
import org.eclipse.emf.diffmerge.bridge.examples.apa2capella.util.CapellaUtil;
import org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob;
import org.eclipse.emf.diffmerge.bridge.interactive.EMFInteractiveBridge;
import org.eclipse.emf.diffmerge.bridge.interactive.util.ResourceUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.polarsys.capella.common.platform.sirius.ted.SemanticEditingDomainFactory;
import org.polarsys.capella.core.compare.CapellaDiffPolicy;
import org.polarsys.capella.core.compare.CapellaMergePolicy;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalFunction;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;

/**
 * A specialized bridge job for the APA to Capella incremental transformation.
 * 
 * @author Amine Lajmi
 *
 */
public class Apa2CapellaBridgeJob extends BridgeJob<AScope> {

	/**
	 * Constructor
	 * 
	 * @param context_p the (non-null) source model scope
	 */
	public Apa2CapellaBridgeJob(AScope context_p) {
		super(IApa2CapellaBridgeConstants.APA_BRIDGE_JOB_NAME, context_p,
				context_p.eResource().getURI().trimFileExtension().appendFileExtension(CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION));
	}

	/**
	 * Additional constructor with explicit target uri
	 *
	 * @param context_p the (non-null) source model scope
	 * @param targetURI_p the (non-null) target uri
	 */
	public Apa2CapellaBridgeJob(AScope context_p, URI targetURI_p) {
		super(IApa2CapellaBridgeConstants.APA_BRIDGE_JOB_NAME, context_p, targetURI_p);
	}
	
	/**
	 * @see org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob#getTargetScope(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	protected IEditableModelScope getTargetScope(Resource resource_p) {
		EObject source_p = resource_p.getContents().get(0);
		PhysicalFunction physicalFunctionRoot = CapellaUtil.getPhysicalFunctionRoot(source_p);
		PhysicalComponent physicalSystemRoot = CapellaUtil.getPhysicalSystemRoot(source_p);
		return new NestedModelScope(physicalSystemRoot, physicalFunctionRoot);
	}

	/**
	 * @see org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob#initializeTargetResourceSet()
	 */
	@Override
	protected ResourceSet initializeTargetResourceSet() {
		// Capella-specific editing domain and resource set
		SemanticEditingDomainFactory factory = new SemanticEditingDomainFactory();
		EditingDomain editingDomain = factory.createEditingDomain();
		return editingDomain.getResourceSet();
	}

	/**
	 * Loads trace resource into a separate resource set.
	 * 
	 * @param traceURI_p the (non-null) trace URI
	 * @return trace resource
	 */
	@Override
	protected Resource getCreateTraceResource(URI traceURI_p) {
		ResourceSetImpl rs = new ResourceSetImpl();
		Resource traceResource = ResourceUtil.getCreateResourceForUri(traceURI_p, rs);
		ResourceUtil.ensureLoaded(traceResource);
		return traceResource;
	}

	/**
	 * Returns the resource of the given URI in the given resource set, creating
	 * it if necessary
	 * 
	 * @param uri_p a (non-null) resource URI
	 * @return a non-null resource
	 */
	protected Resource getCreateResource(URI uri_p) {
		ResourceSet rs = getTargetResourceSet();
		Resource result = ResourceUtil.getCreateResourceForUri(uri_p, rs);
		ResourceUtil.ensureLoaded(result);
		return result;
	}

	/**
	 * @see org.eclipse.emf.diffmerge.bridge.interactive.BridgeJob#getBridge()
	 */
	@Override
	protected EMFInteractiveBridge<AScope, IEditableModelScope> getBridge() {
		Apa2CapellaBridge mappingBridge = new Apa2CapellaBridge();
		// Make the mapping bridge incremental
		EMFInteractiveBridge<AScope, IEditableModelScope> result = new EMFInteractiveBridge<AScope, IEditableModelScope>(
				mappingBridge, new CapellaDiffPolicy(), new CapellaMergePolicy(), null);
		return result;
	}
}