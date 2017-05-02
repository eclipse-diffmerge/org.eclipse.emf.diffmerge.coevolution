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

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction;
import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;


/**
 * A base implementation of ISymbolFunction.
 * @author Olivier Constant
 */
public class BaseSymbolFunction implements ISymbolFunction {
  
  /** The singleton instance */
  private static final BaseSymbolFunction INSTANCE = new BaseSymbolFunction();
  
  
  /**
   * Default constructor
   */
  public BaseSymbolFunction() {
    // Stateless
  }
  
  /**
   * Append a symbol for the given decomposed composite object to the given string builder
   * Precondition: isComposite(composite_p)
   * @param composite_p a non-null object
   * @param builder_p a non-null string builder
   */
  protected void addCompositeSymbol(Object composite_p, StringBuilder builder_p) {
    Iterable<?> decomposed = decompose(composite_p);
    addDecomposedCompositeSymbol(decomposed, builder_p);
  }
  
  /**
   * Append a symbol for the given decomposed composite object to the given string builder
   * @param composite_p a non-null iterable
   * @param builder_p a non-null string builder
   */
  protected void addDecomposedCompositeSymbol(Iterable<?> composite_p,
      StringBuilder builder_p) {
    builder_p.append('(');
    boolean first = true;
    for (Object element : composite_p) {
      if (first) {
        first = false;
      } else {
        builder_p.append(',');
        builder_p.append(' ');
      }
      addSymbol(element, builder_p);
    }
    builder_p.append(')');
  }
  
  /**
   * Append a symbol for the given object to the given string builder
   * @param object_p a potentially null object
   * @param builder_p a non-null string builder
   */
  protected void addSymbol(Object object_p, StringBuilder builder_p) {
    if (object_p instanceof ISymbolProvider) {
      // Let the object provide its symbol
      Object ownSymbol = ((ISymbolProvider)object_p).getSymbol(this);
      builder_p.append(ownSymbol);
    } else if (isComposite(object_p)) {
      // Provide a symbol for the "composite" object
      addCompositeSymbol(object_p, builder_p);
    } else {
      // Provide a symbol for the "atomic" object
      Object atomSymbol = getAtomSymbol(object_p);
      builder_p.append(atomSymbol);
    }
  }
  
  /**
   * Return a decomposed variant of the given composite object,
   * where the order of the returned iterable is significant
   * with respect to the symbolic representation
   * Precondition: isComposite(object_p)
   * @param object_p a non-null object
   * @return a non-null, potentially empty iterable
   */
  protected Iterable<?> decompose(Object object_p) {
    Iterable<?> result;
    Iterable<?> iterable = (Iterable<?>)object_p; // Thanks to precondition
    if (isOrderSignificant(iterable)) {
      result = iterable;
    } else {
      result = order(iterable);
    }
    return result;
  }
  
  /**
   * Return a symbol for the given "atomic" object
   * Precondition: !isComposite(object_p)
   * @param object_p a potentially null object
   * @return a potentially null object
   */
  protected Object getAtomSymbol(Object object_p) {
    return object_p == null? null: object_p.toString();
  }
  
  /**
   * Return the singleton instance of this class
   * @return a non-null object
   */
  public static BaseSymbolFunction getInstance() {
    return INSTANCE;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.ISymbolFunction#getSymbol(java.lang.Object)
   */
  public Object getSymbol(Object object_p) {
    StringBuilder builder = new StringBuilder();
    addSymbol(object_p, builder);
    return builder.toString();
  }
  
  /**
   * Return whether the given object must be considered as composite w.r.t.
   * symbolic representation
   * @param object_p a potentially null object
   */
  protected boolean isComposite(Object object_p) {
    return object_p instanceof Iterable<?>;
  }
  
  /**
   * Return whether the order of elements in the given iterable must be considered
   * significant with respect to its symbolic representation
   * @param iterable_p a non-null iterable
   */
  protected boolean isOrderSignificant(Iterable<?> iterable_p) {
    return iterable_p instanceof List<?>;
  }
  
  /**
   * Return an iterable with similar content as the given one but with
   * an order of the elements that is significant with respect to symbolic
   * representation
   * @param iterable_p a non-null object
   * @return a non-null, potentially empty iterable
   */
  protected Iterable<?> order(Iterable<?> iterable_p) {
    // The fundamental assumption here is that every element in the given
    // iterable has a non-null symbol and all such symbols are comparable
    Map<Object, Object> result = new TreeMap<Object, Object>();
    for (Object part : iterable_p) {
      Object partSymbol = getSymbol(part);
      result.put(partSymbol, part);
    }
    return result.values();
  }
  
}