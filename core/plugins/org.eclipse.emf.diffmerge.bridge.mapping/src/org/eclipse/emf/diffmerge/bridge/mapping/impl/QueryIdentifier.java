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
