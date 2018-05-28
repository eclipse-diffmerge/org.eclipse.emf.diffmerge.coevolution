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
package org.eclipse.emf.diffmerge.bridge.util.structures;

import java.util.Set;


/**
 * A pure data structure whose elements can be retrieved using fields,
 * similarly to C structs or OCL tuples.
 * This class has no type parameter since the type of the elements is checked through fields.
 * @see IPureStructure
 * @author Olivier Constant
 */
public interface IStruct extends IPureStructure<Object> {
  
  /**
   * Return the value of the given field
   * @param field_p a non-null field
   * @throws IllegalArgumentException if !getFields().contains(field_p)
   * @return a potentially null object
   */
  <T> T get(IField<T> field_p);
  
  /**
   * Return the fields for this struct in ascending order.
   * The order of the fields is constant but it does not necessarily reflect the order
   * of the corresponding elements in asCollection().
   * @return a non-null, non-empty, unmodifiable sorted set containing no null value
   */
  Set<? extends IField<?>> getFields();
  
  /**
   * Return whether the struct owns the given field
   * Postcondition: result == getFields().contains(field_p)
   * @param field_p a non-null field
   */
  boolean hasField(IField<?> field_p);
  
  
  /**
   * Editable structs.
   */
  interface Editable extends IStruct {
    /**
     * Set the value for the given field
     * Precondition: getFields().contains(field_p)
     * @param field_p a non-null field
     * @param value_p a potentially null value
     */
    <T> void set(IField<T> field_p, T value_p);
  }
  
  
  /**
   * Structs whose set of fields can be dynamically modified.
   */
  interface Dynamic extends Editable {
    /**
     * Add the given field to the struct
     * Postcondition: getFields().contains(field_p)
     * @param field_p a non-null field
     * @return true if and only if the struct did not contain the field (!getFields().contains(field_p))
     */
    boolean addField(IField<?> field_p);
    
    /**
     * Remove the given field from the struct
     * Postcondition: !getFields().contains(field_p)
     * @param field_p a non-null field
     * @return the value for the field, or null if none or if the struct did not contain the field
     */
    <T> T removeField(IField<T> field_p);
  }
  
}
