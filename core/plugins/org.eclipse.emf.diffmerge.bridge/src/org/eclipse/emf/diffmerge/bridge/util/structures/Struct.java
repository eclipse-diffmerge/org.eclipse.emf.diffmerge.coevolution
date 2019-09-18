/*********************************************************************
 * Copyright (c) 2014-2019 Thales Global Services S.A.S.
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

import static org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil.areEqual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;


/**
 * A base implementation of IStruct.
 * @see IStruct
 * @author Olivier Constant
 */
public class Struct extends AbstractPureStructure<Object> implements IStruct.Editable {
  
  /**
   * Create and return a new struct based on the given fields
   * @param fields_p a non-null array or succession of fields
   * @return a non-null struct
   */
  public static Struct struct(IField<?>... fields_p) {
    return new Struct(fields_p);
  }
  
  
  /** The non-null map from fields to values with fields ordered */
  protected final SortedMap<IField<?>, Object> _contentMap;
  
  
  /**
   * Constructor
   * @param fields_p a non-null, possibly empty array or succession of fields (null values and duplicates are ignored)
   */
  public Struct(IField<?>... fields_p) {
    this(Arrays.asList(fields_p));
  }
  
  /**
   * Constructor
   * @param fields_p a non-null, possibly empty list of fields (null values and duplicates are ignored)
   */
  public Struct(List<? extends IField<?>> fields_p) {
    _contentMap = new TreeMap<IField<?>, Object>();
    for (IField<?> field : fields_p) {
      if (field != null)
        _contentMap.put(field, null);
    }
  }
  
  /**
   * Constructor
   * @param valueMap_p a non-null, possibly empty map of (field, value) (null fields are ignored)
   */
  public Struct(Map<? extends IField<?>, ?> valueMap_p) {
    _contentMap = new TreeMap<IField<?>, Object>();
    for (Map.Entry<? extends IField<?>, ?> entry : valueMap_p.entrySet()) {
      IField<?> key = entry.getKey();
      if (key != null)
        _contentMap.put(key, entry.getValue());
    }
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#asCollection()
   */
  public Collection<Object> asCollection() {
    return _contentMap.values();
  }
  
  /**
   * Check that this struct contains the given field, otherwise throw an exception
   * @param field_p a possibly null field
   */
  protected void checkField(IField<?> field_p) {
    if (!hasField(field_p))
      throw new IllegalArgumentException("Struct does not have field: " + field_p); //$NON-NLS-1$
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object peer_p) {
    if (peer_p == this)
      return true;
    if (!(peer_p instanceof IStruct))
      return false;
    if (peer_p instanceof Struct)
      return _contentMap.equals(((Struct)peer_p)._contentMap);
    // Peer instance of IStruct but not Struct
    IStruct peer = (IStruct)peer_p;
    Set<? extends IField<?>> ownFields = getFields();
    if (ownFields.size() != peer.getFields().size())
      return false;
    for (IField<?> field : ownFields) {
      boolean sameValue = false;
      try {
        Object peerValue = peer.get(field);
        Object ownValue = get(field);
        sameValue = areEqual(ownValue, peerValue);
      } catch (IllegalArgumentException e) {
        // Proceed
      }
      if (!sameValue)
        return false;
    }
    return true;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IStruct#get(org.eclipse.emf.diffmerge.bridge.util.structures.IField)
   */
  public <T> T get(IField<T> field_p) {
    @SuppressWarnings("unchecked")
    T result = (T)_contentMap.get(field_p); // Type OK thanks to the contract of set(...)
    if (result == null)
      checkField(field_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure#getContents()
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Collection<Tuple2<IField<?>, Object>> getContents() {
    Set<Map.Entry<IField<?>, Object>> entries = _contentMap.entrySet();
    List<Tuple2<IField<?>, Object>> result =
        new ArrayList<Tuple2<IField<?>,Object>>(entries.size());
    for (Map.Entry<IField<?>, Object> entry : entries) {
      result.add((Tuple2)Tuples.tuple(entry.getKey(), entry.getValue()));
    }
    return Collections.unmodifiableCollection(result);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IStruct#getFields()
   */
  public Set<? extends IField<?>> getFields() {
    return _contentMap.keySet();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider#getSymbol(org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction)
   */
  public Object getSymbol(ISymbolFunction function_p) {
    StringBuilder builder = new StringBuilder();
    builder.append('(');
    boolean first = true;
    for (IField<?> field : getFields()) {
      if (first) {
        first = false;
      } else {
        builder.append(',');
        builder.append(' ');
      }
      // Field
      Object fieldSymbol = function_p.getSymbol(field);
      // Value
      Object value = get(field);
      Object valueSymbol = function_p.getSymbol(value);
      builder.append(fieldSymbol);
      builder.append(':');
      builder.append(valueSymbol);
    }
    builder.append(')');
    return builder.toString();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IStruct#hasField(org.eclipse.emf.diffmerge.bridge.util.structures.IField)
   */
  public boolean hasField(IField<?> field_p) {
    return getFields().contains(field_p);
  }
  
  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return _contentMap.hashCode();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.IStruct.Editable#set(org.eclipse.emf.diffmerge.bridge.util.structures.IField, java.lang.Object)
   */
  public <T> void set(IField<T> field_p, T value_p) {
    checkField(field_p);
    _contentMap.put(field_p, value_p);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.structures.AbstractPureStructure#size()
   */
  @Override
  public int size() {
    return _contentMap.size();
  }
  
}
