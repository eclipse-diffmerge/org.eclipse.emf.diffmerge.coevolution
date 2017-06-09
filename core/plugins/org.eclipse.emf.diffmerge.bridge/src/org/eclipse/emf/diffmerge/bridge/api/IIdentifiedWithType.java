/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.api;


/**
 * Objects that have a unique identifier of a given type.
 * @param <T> the type of the identifiers
 * @author Olivier Constant
 */
public interface IIdentifiedWithType<T> {
  
  /**
   * Return the unique identifier of the receiver
   * @return a non-null object
   */
  T getID();
  
}
