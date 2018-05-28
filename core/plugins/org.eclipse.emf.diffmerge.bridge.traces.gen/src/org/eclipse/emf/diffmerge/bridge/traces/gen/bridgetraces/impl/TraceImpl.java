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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.impl.emf.EMFSymbolFunction;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.BridgetracesPackage;
import org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.Trace;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl#getSymbolFunction <em>Symbol Function</em>}</li>
 *   <li>{@link org.eclipse.emf.diffmerge.bridge.traces.gen.bridgetraces.impl.TraceImpl#getTargetToCause <em>Target To Cause</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TraceImpl extends MinimalEObjectImpl.Container implements Trace {
  /**
   * The default value of the '{@link #getSymbolFunction() <em>Symbol Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSymbolFunction()
   * @generated
   * @ordered
   */
  protected static final ISymbolFunction SYMBOL_FUNCTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSymbolFunction() <em>Symbol Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSymbolFunction()
   * @generated
   * @ordered
   */
  protected ISymbolFunction symbolFunction = SYMBOL_FUNCTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getTargetToCause() <em>Target To Cause</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetToCause()
   * @generated
   * @ordered
   */
  protected EMap<String, String> targetToCause;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  protected TraceImpl() {
    super();
    symbolFunction = EMFSymbolFunction.getInstance(); // Default value
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return BridgetracesPackage.Literals.TRACE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap<String, String> getTargetToCause() {
    if (targetToCause == null) {
      targetToCause = new EcoreEMap<String,String>(BridgetracesPackage.Literals.TRACE_ENTRY, TraceEntryImpl.class, this, BridgetracesPackage.TRACE__TARGET_TO_CAUSE);
    }
    return targetToCause;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ISymbolFunction getSymbolFunction() {
    return symbolFunction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case BridgetracesPackage.TRACE__TARGET_TO_CAUSE:
        return ((InternalEList<?>)getTargetToCause()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case BridgetracesPackage.TRACE__SYMBOL_FUNCTION:
        return getSymbolFunction();
      case BridgetracesPackage.TRACE__TARGET_TO_CAUSE:
        if (coreType) return getTargetToCause();
        else return getTargetToCause().map();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case BridgetracesPackage.TRACE__SYMBOL_FUNCTION:
        setSymbolFunction((ISymbolFunction)newValue);
        return;
      case BridgetracesPackage.TRACE__TARGET_TO_CAUSE:
        ((EStructuralFeature.Setting)getTargetToCause()).set(newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case BridgetracesPackage.TRACE__SYMBOL_FUNCTION:
        setSymbolFunction(SYMBOL_FUNCTION_EDEFAULT);
        return;
      case BridgetracesPackage.TRACE__TARGET_TO_CAUSE:
        getTargetToCause().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case BridgetracesPackage.TRACE__SYMBOL_FUNCTION:
        return SYMBOL_FUNCTION_EDEFAULT == null ? symbolFunction != null : !SYMBOL_FUNCTION_EDEFAULT.equals(symbolFunction);
      case BridgetracesPackage.TRACE__TARGET_TO_CAUSE:
        return targetToCause != null && !targetToCause.isEmpty();
    }
    return super.eIsSet(featureID);
  }
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (symbolFunction: ");
    result.append(symbolFunction);
    result.append(')');
    return result.toString();
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace#getCause(java.lang.Object)
   * @generated NOT
   */
  public String getCause(Object target_p) {
    String result = null;
    if (target_p != null) {
      Object targetSymbol = getSymbolFunction().getSymbol(target_p);
      if (targetSymbol != null)
        result = getTargetToCause().get(getPersistentForm(targetSymbol));
    }
    return result;
  }
  
  /**
   * Return the persistent representation of the given symbol.
   * @param symbol_p a potentially null object
   * @return a object which is not null if symbol_p is not null
   * @generated NOT
   */
  protected String getPersistentForm(Object symbol_p) {
    // Assumes that different symbols have different string representations
    return symbol_p == null? null: symbol_p.toString();
  }
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSymbolFunction(ISymbolFunction newSymbolFunction) {
    ISymbolFunction oldSymbolFunction = symbolFunction;
    symbolFunction = newSymbolFunction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, BridgetracesPackage.TRACE__SYMBOL_FUNCTION, oldSymbolFunction, symbolFunction));
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace.Editable#putCause(java.lang.Object, java.lang.Object)
   * @generated NOT
   */
  public String putCause(Object cause_p, Object target_p) {
    ISymbolFunction symbolFunction = getSymbolFunction();
    Object targetSymbol = symbolFunction.getSymbol(target_p);
    if (targetSymbol == null)
      throwNoTargetSymbolException(target_p);
    Object causeSymbol = symbolFunction.getSymbol(cause_p);
    if (causeSymbol == null)
      throwNoCauseSymbolException(cause_p, target_p);
    return getTargetToCause().put(getPersistentForm(targetSymbol), getPersistentForm(causeSymbol));
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace.Editable#removeTarget(java.lang.Object)
   * @generated NOT
   */
  public Object removeTarget(Object target_p) {
    ISymbolFunction symbolFunction = getSymbolFunction();
    Object targetSymbol = symbolFunction.getSymbol(target_p);
    if (targetSymbol == null)
      throwNoTargetSymbolException(target_p);
    return getTargetToCause().removeKey(getPersistentForm(targetSymbol));
  }
  
  /**
   * Throw an exception due to the inability to generate a symbol for the given cause
   * associated to the given target
   * @param cause_p a non-null object
   * @param target_p a non-null object
   * @generated NOT
   */
  protected void throwNoCauseSymbolException(Object cause_p, Object target_p) {
    throw new IllegalArgumentException(
        String.format(
            "Cannot get trace symbol for cause of target data presence: [%1$s] -> [%2$s].",
            cause_p, target_p));
  }
  
  /**
   * Throw an exception due to the inability to generate a symbol for the given target
   * @param target_p a non-null object
   * @generated NOT
   */
  protected void throwNoTargetSymbolException(Object target_p) {
    throw new IllegalArgumentException(
        String.format(
            "Cannot get trace symbol for target data [%1$s].", target_p));
  }
  
} //TraceImpl
