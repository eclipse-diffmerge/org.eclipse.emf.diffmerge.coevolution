/**
 * <copyright>
 * 
 * Copyright (c) 2014-2016 Thales Global Services S.A.S.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesFactory
 * @model kind="package"
 * @generated
 */
public interface BridgetracesPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "bridgetraces";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/emf/diffmerge/bridge/1.0.0/traces";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "org.eclipse.emf.diffmerge.bridge.traces.gen";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  BridgetracesPackage eINSTANCE = org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable <em>ITrace</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getITrace()
   * @generated
   */
  int ITRACE = 2;

  /**
   * The number of structural features of the '<em>ITrace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITRACE_FEATURE_COUNT = 0;

  /**
   * The number of operations of the '<em>ITrace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITRACE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl <em>Trace</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getTrace()
   * @generated
   */
  int TRACE = 0;

  /**
   * The feature id for the '<em><b>Symbol Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE__SYMBOL_FUNCTION = ITRACE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Target To Cause</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE__TARGET_TO_CAUSE = ITRACE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Trace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_FEATURE_COUNT = ITRACE_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>Trace</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_OPERATION_COUNT = ITRACE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceEntryImpl <em>Trace Entry</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceEntryImpl
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getTraceEntry()
   * @generated
   */
  int TRACE_ENTRY = 1;

  /**
   * The feature id for the '<em><b>Key</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_ENTRY__KEY = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_ENTRY__VALUE = 1;

  /**
   * The number of structural features of the '<em>Trace Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_ENTRY_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Trace Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRACE_ENTRY_OPERATION_COUNT = 0;


  /**
   * The meta object id for the '<em>ISymbol Function</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getISymbolFunction()
   * @generated
   */
  int ISYMBOL_FUNCTION = 3;

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace <em>Trace</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trace</em>'.
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace
   * @generated
   */
  EClass getTrace();

  /**
   * Returns the meta object for the map '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getTargetToCause <em>Target To Cause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>Target To Cause</em>'.
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getTargetToCause()
   * @see #getTrace()
   * @generated
   */
  EReference getTrace_TargetToCause();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getSymbolFunction <em>Symbol Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Symbol Function</em>'.
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace#getSymbolFunction()
   * @see #getTrace()
   * @generated
   */
  EAttribute getTrace_SymbolFunction();

  /**
   * Returns the meta object for class '{@link java.util.Map.Entry <em>Trace Entry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trace Entry</em>'.
   * @see java.util.Map.Entry
   * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
   *        valueDataType="org.eclipse.emf.ecore.EString" valueRequired="true"
   * @generated
   */
  EClass getTraceEntry();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see java.util.Map.Entry
   * @see #getTraceEntry()
   * @generated
   */
  EAttribute getTraceEntry_Key();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see java.util.Map.Entry
   * @see #getTraceEntry()
   * @generated
   */
  EAttribute getTraceEntry_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable <em>ITrace</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>ITrace</em>'.
   * @see org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable
   * @model instanceClass="org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace$Editable"
   * @generated
   */
  EClass getITrace();

  /**
   * Returns the meta object for data type '{@link org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction <em>ISymbol Function</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>ISymbol Function</em>'.
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction
   * @model instanceClass="org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction" serializeable="false"
   * @generated
   */
  EDataType getISymbolFunction();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  BridgetracesFactory getBridgetracesFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl <em>Trace</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getTrace()
     * @generated
     */
    EClass TRACE = eINSTANCE.getTrace();

    /**
     * The meta object literal for the '<em><b>Target To Cause</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRACE__TARGET_TO_CAUSE = eINSTANCE.getTrace_TargetToCause();

    /**
     * The meta object literal for the '<em><b>Symbol Function</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRACE__SYMBOL_FUNCTION = eINSTANCE.getTrace_SymbolFunction();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceEntryImpl <em>Trace Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceEntryImpl
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getTraceEntry()
     * @generated
     */
    EClass TRACE_ENTRY = eINSTANCE.getTraceEntry();

    /**
     * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRACE_ENTRY__KEY = eINSTANCE.getTraceEntry_Key();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRACE_ENTRY__VALUE = eINSTANCE.getTraceEntry_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable <em>ITrace</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace.Editable
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getITrace()
     * @generated
     */
    EClass ITRACE = eINSTANCE.getITrace();

    /**
     * The meta object literal for the '<em>ISymbol Function</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction
     * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.BridgetracesPackageImpl#getISymbolFunction()
     * @generated
     */
    EDataType ISYMBOL_FUNCTION = eINSTANCE.getISymbolFunction();

  }

} //BridgetracesPackage
