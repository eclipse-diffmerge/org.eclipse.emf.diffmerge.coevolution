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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import org.eclipse.emf.diffmerge.bridge.impl.AbstractNamedElement;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryIdentifier;


/**
 * A simple implementation of IQueryIdentifier.
 * @param <O> the type of the outputs of the query
 * @author Olivier Constant
 */
public class QueryIdentifier<O> extends AbstractNamedElement implements IQueryIdentifier<O> {
  
  /**
   * Default constructor
   */
  public QueryIdentifier() {
    super();
  }
  
  /**
   * Constructor
   * @param name_p a non-null name
   */
  public QueryIdentifier(String name_p) {
    super(name_p);
  }
  
}
