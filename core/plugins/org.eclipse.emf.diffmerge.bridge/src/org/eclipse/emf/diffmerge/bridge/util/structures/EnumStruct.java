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
package org.eclipse.emf.diffmerge.bridge.util.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;


/**
 * A struct whose fields are defined by an enumeration.
 * @see IStruct
 * @author Olivier Constant
 */
public class EnumStruct<E extends Enum<E>> extends Struct {
  
  /**
   * An implementation of IField that encapsulates an enumeration literal.
   * @param <E> the type of the literal
   * @param <T> the type of the possible values of the field
   */
  public static class EnumField<E extends Enum<E>, T>
  implements IField<T>, Comparable<EnumField<E, ?>> {
    /** The non-null literal */
    private final E _literal;
    /**
     * Constructor
     * @param literal_p the non-null literal
     */
    public EnumField(E literal_p) {
      _literal = literal_p;
    }
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(EnumField<E, ?> o_p) {
      return getLiteral().compareTo(o_p.getLiteral());
    }
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object_p) {
      boolean result = false;
      if (object_p instanceof EnumField<?, ?>) {
        EnumField<?, ?> peer = (EnumField<?, ?>)object_p;
        result = getLiteral().equals(peer.getLiteral());
      }
      return result;
    }
    /**
     * Return the literal of the field
     * @return a non-null object
     */
    public E getLiteral() {
      return _literal;
    }
    /**
     * @see org.eclipse.emf.diffmerge.bridge.util.structures.IField#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
     */
    public String getSymbol(ISymbolFunction function_p) {
      return _literal.name();
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return getLiteral().hashCode();
    }
  }
  
  
  /**
   * Constructor
   * @param fields_p a non-null, possibly empty array or succession of fields (null values and duplicates are ignored)
   */
  public EnumStruct(List<? extends EnumField<E, ?>> fields_p) {
    super(fields_p);
  }
  
  /**
   * Constructor
   * @param enumeration_p the non-null enumeration whose literals define fields
   */
  public EnumStruct(Class<E> enumeration_p) {
    this(generateFields(enumeration_p));
  }
  
  /**
   * Create and return fields that correspond to the literals of the given enumeration
   * @param enumeration_p a non-null enumeration type
   * @return a non-null, potentially empty list
   */
  protected static <E extends Enum<E>> List<EnumField<E, ?>> generateFields(Class<E> enumeration_p) {
    E[] literals = enumeration_p.getEnumConstants();
    List<EnumField<E, ?>> result = new ArrayList<EnumField<E, ?>>(literals.length);
    for (E literal : literals) {
      EnumField<E, Object> field = new EnumField<E, Object>(literal);
      result.add(field);
    }
    return result;
  }
  
  /**
   * Return the value of the field for the given literal
   * @param literal_p a non-null object
   * @return a potentially null object
   */
  @SuppressWarnings("unchecked")
  public <U> U get(E literal_p) {
    U result = null;
    IField<?> field = getField(literal_p);
    if (field != null)
      result = (U)get(field);
    return result;
  }
  
  /**
   * Return the field that corresponds to the given literal, if any
   * @param literal_p a non-null literal
   * @return a potentially null object
   */
  public EnumField<E, ?> getField(E literal_p) {
    for (EnumField<E, ?> field : getFields()) {
      if (field.getLiteral() == literal_p)
        return field;
    }
    return null;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.Struct#getFields()
   */
  @Override
  @SuppressWarnings("unchecked")
  public Set<EnumField<E, ?>> getFields() {
    return (Set<EnumField<E, ?>>)super.getFields();
  }
  
  /**
   * Set the value for the given literal
   * @param literal_p a non-null literal
   * @param value_p a potentially null value
   * @throws IllegalArgumentException if no field corresponds to the literal
   */
  @SuppressWarnings("unchecked")
  public void set(E literal_p, Object value_p) {
    EnumField<E, ?> field = getField(literal_p);
    if (field == null)
      throw new IllegalArgumentException("Field is not present for literal: " + literal_p.name()); //$NON-NLS-1$
    set((IField<Object>)field, value_p);
  }
  
}
