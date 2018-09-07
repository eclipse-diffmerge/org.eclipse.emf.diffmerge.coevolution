/*********************************************************************
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
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
