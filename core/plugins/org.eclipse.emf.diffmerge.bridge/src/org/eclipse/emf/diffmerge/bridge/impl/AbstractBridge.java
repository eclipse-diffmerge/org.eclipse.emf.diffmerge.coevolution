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
package org.eclipse.emf.diffmerge.bridge.impl;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;


/**
 * A base implementation of IBridge.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @see IBridge
 * @author Olivier Constant
 */
public abstract class AbstractBridge<SD, TD> implements IBridge<SD, TD> {
  
  /**
   * Add the given target data element into the given target data set, if feasible
   * @param targetDataSet_p a non-null object
   * @param target_p a non-null object
   * @return whether the operation succeeded
   */
  protected abstract boolean addElementaryTarget(TD targetDataSet_p, Object target_p);
  
  /**
   * Add the given target data into the given target data set, if feasible
   * @param targetDataSet_p a non-null object
   * @param target_p a non-null object
   * @return whether the operation succeeded
   */
  public boolean addTarget(TD targetDataSet_p, Object target_p) {
    // Decompose pure structures
    Collection<?> targetElements;
    if (target_p instanceof IPureStructure<?>) {
      targetElements = ((IPureStructure<?>)target_p).asCollection();
    } else {
      targetElements = Collections.singleton(target_p);
    }
    for (Object targetElement : targetElements) {
      boolean OK = addElementaryTarget(targetDataSet_p, targetElement);
      if (!OK) return false;
    }
    return true;
  }
  
}
