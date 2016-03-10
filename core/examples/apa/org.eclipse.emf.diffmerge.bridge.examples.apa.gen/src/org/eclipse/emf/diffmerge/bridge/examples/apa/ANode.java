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
package org.eclipse.emf.diffmerge.bridge.examples.apa;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ANode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.examples.apa.ANode#getOwningScope <em>Owning Scope</em>}</li>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.examples.apa.ANode#getBehaviors <em>Behaviors</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.diffmerge.bridge.examples.apa.ApaPackage#getANode()
 * @model
 * @generated
 */
public interface ANode extends ANamedElement {
	/**
   * Returns the value of the '<em><b>Owning Scope</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link org.eclipse.emf.diffmerge.bridge.examples.apa.AScope#getNodes <em>Nodes</em>}'.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owning Scope</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Owning Scope</em>' container reference.
   * @see #setOwningScope(AScope)
   * @see org.eclipse.emf.diffmerge.bridge.examples.apa.ApaPackage#getANode_OwningScope()
   * @see org.eclipse.emf.diffmerge.bridge.examples.apa.AScope#getNodes
   * @model opposite="nodes" required="true" transient="false"
   * @generated
   */
	AScope getOwningScope();

	/**
   * Sets the value of the '{@link org.eclipse.emf.diffmerge.bridge.examples.apa.ANode#getOwningScope <em>Owning Scope</em>}' container reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Owning Scope</em>' container reference.
   * @see #getOwningScope()
   * @generated
   */
	void setOwningScope(AScope value);

	/**
   * Returns the value of the '<em><b>Behaviors</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.diffmerge.bridge.examples.apa.ABehavior}.
   * It is bidirectional and its opposite is '{@link org.eclipse.emf.diffmerge.bridge.examples.apa.ABehavior#getOwningNode <em>Owning Node</em>}'.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behaviors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Behaviors</em>' containment reference list.
   * @see org.eclipse.emf.diffmerge.bridge.examples.apa.ApaPackage#getANode_Behaviors()
   * @see org.eclipse.emf.diffmerge.bridge.examples.apa.ABehavior#getOwningNode
   * @model opposite="owningNode" containment="true"
   * @generated
   */
	EList<ABehavior> getBehaviors();

} // ANode
