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
package org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl;

import java.util.Map;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.api.incremental.ISymbolBasedBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesFactory;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BridgetracesPackageImpl extends EPackageImpl implements BridgetracesPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass traceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass traceEntryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iTraceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType iSymbolFunctionEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private BridgetracesPackageImpl() {
    super(eNS_URI, BridgetracesFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link BridgetracesPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static BridgetracesPackage init() {
    if (isInited) return (BridgetracesPackage)EPackage.Registry.INSTANCE.getEPackage(BridgetracesPackage.eNS_URI);

    // Obtain or create and register package
    BridgetracesPackageImpl theBridgetracesPackage = (BridgetracesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BridgetracesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BridgetracesPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theBridgetracesPackage.createPackageContents();

    // Initialize created meta-data
    theBridgetracesPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theBridgetracesPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(BridgetracesPackage.eNS_URI, theBridgetracesPackage);
    return theBridgetracesPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTrace() {
    return traceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTrace_TargetToCause() {
    return (EReference)traceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTrace_SymbolFunction() {
    return (EAttribute)traceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTraceEntry() {
    return traceEntryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTraceEntry_Key() {
    return (EAttribute)traceEntryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTraceEntry_Value() {
    return (EAttribute)traceEntryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getITrace() {
    return iTraceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getISymbolFunction() {
    return iSymbolFunctionEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BridgetracesFactory getBridgetracesFactory() {
    return (BridgetracesFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    traceEClass = createEClass(TRACE);
    createEAttribute(traceEClass, TRACE__SYMBOL_FUNCTION);
    createEReference(traceEClass, TRACE__TARGET_TO_CAUSE);

    traceEntryEClass = createEClass(TRACE_ENTRY);
    createEAttribute(traceEntryEClass, TRACE_ENTRY__KEY);
    createEAttribute(traceEntryEClass, TRACE_ENTRY__VALUE);

    iTraceEClass = createEClass(ITRACE);

    // Create data types
    iSymbolFunctionEDataType = createEDataType(ISYMBOL_FUNCTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    traceEClass.getESuperTypes().add(this.getITrace());

    // Initialize classes, features, and operations; add parameters
    initEClass(traceEClass, Trace.class, "Trace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTrace_SymbolFunction(), this.getISymbolFunction(), "symbolFunction", null, 1, 1, Trace.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTrace_TargetToCause(), this.getTraceEntry(), null, "targetToCause", null, 0, -1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(traceEntryEClass, Map.Entry.class, "TraceEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTraceEntry_Key(), ecorePackage.getEString(), "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTraceEntry_Value(), ecorePackage.getEString(), "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(iTraceEClass, ISymbolBasedBridgeTrace.Editable.class, "ITrace", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);

    // Initialize data types
    initEDataType(iSymbolFunctionEDataType, ISymbolFunction.class, "ISymbolFunction", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //BridgetracesPackageImpl
