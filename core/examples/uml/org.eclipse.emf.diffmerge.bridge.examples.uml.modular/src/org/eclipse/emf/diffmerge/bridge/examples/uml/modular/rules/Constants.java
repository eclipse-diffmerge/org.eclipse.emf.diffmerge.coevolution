/**
 * <copyright>
 * 
 * Copyright (c) 2015-2018 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.examples.uml.modular.rules;


/**
 * The location of common constants.
 * @author Olivier Constant
 */
public interface Constants {
  
  /** For the rule that uses EnumStructs */
  public enum AssocElements {
    /** The UML association */
    ASSOC,
    /** The source property of the association */
    SRC_PROP,
    /** The target property of the association */
    TARGET_PROP
  }
  
}
