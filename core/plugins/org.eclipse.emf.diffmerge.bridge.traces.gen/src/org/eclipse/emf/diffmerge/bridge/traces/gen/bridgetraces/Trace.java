/**
 * <copyright>
 * 
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getSymbolFunction <em>Symbol Function</em>}</li>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getTargetToCause <em>Target To Cause</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage#getTrace()
 * @model superTypes="org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.ITrace"
 * @generated
 */
public interface Trace extends EObject, ISymbolBasedBridgeTrace.Editable {
  /**
   * Returns the value of the '<em><b>Target To Cause</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target To Cause</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target To Cause</em>' map.
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage#getTrace_TargetToCause()
   * @model mapType="org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.TraceEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
   * @generated
   */
  EMap<String, String> getTargetToCause();

  /**
   * Returns the value of the '<em><b>Symbol Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Symbol Function</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Symbol Function</em>' attribute.
   * @see #setSymbolFunction(ISymbolFunction)
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage#getTrace_SymbolFunction()
   * @model dataType="org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.ISymbolFunction" required="true" transient="true"
   * @generated
   */
  ISymbolFunction getSymbolFunction();

  /**
   * Sets the value of the '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getSymbolFunction <em>Symbol Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Symbol Function</em>' attribute.
   * @see #getSymbolFunction()
   * @generated
   */
  void setSymbolFunction(ISymbolFunction value);

} // Trace
