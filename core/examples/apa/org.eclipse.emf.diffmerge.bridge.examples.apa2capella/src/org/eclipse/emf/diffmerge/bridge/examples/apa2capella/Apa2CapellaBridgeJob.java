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
import org.eclipse.emf.diffmerge.bridge.capella.integration.CapellaBridgeJob;
import org.eclipse.emf.diffmerge.bridge.examples.apa.AScope;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingBridge;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;


/**
 * A specialized bridge job for the APA to Capella incremental transformation.
 * 
 * @author Amine Lajmi
 *
 */
public class Apa2CapellaBridgeJob extends CapellaBridgeJob<AScope> {
  
	/**
	 * Constructor
	 * @param context_p the (non-null) source model scope
	 */
	public Apa2CapellaBridgeJob(AScope context_p) {
		super(IApa2CapellaBridgeConstants.APA_BRIDGE_JOB_NAME, context_p,
				context_p.eResource().getURI().trimFileExtension().appendFileExtension(CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION));
	}
	
	/**
	 * Additional constructor with explicit target URI
	 * @param context_p the (non-null) source model scope
	 * @param targetURI_p the (non-null) target URI
	 */
	public Apa2CapellaBridgeJob(AScope context_p, URI targetURI_p) {
		super(IApa2CapellaBridgeConstants.APA_BRIDGE_JOB_NAME, context_p, targetURI_p);
	}
	
	/**
	 * @see org.eclipse.emf.diffmerge.bridge.capella.integration.CapellaBridgeJob#createMappingBridge()
	 */
	@Override
	protected IMappingBridge<AScope, IEditableModelScope> createMappingBridge() {
	  return new Apa2CapellaBridge();
	}
	
}