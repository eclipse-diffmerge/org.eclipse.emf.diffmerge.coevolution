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
package org.eclipse.emf.diffmerge.bridge.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.diffmerge.bridge.util.structures.Tuple2;


/**
 * A utility class which provides simple facilities for defining collections
 * in a declarative style. This is required e.g. for defining constant maps in
 * interfaces.
 * @author Olivier Constant
 */
public final class CollectionsUtil {
  
  /**
   * Default constructor
   */
  private CollectionsUtil() {
    // Forbids instantiation
  }
  
  /**
   * A trivial empty iterator. 
   */
  private static class EmptyIterator implements Iterator<Object> {
    /**
     * Singleton pattern
     */
    public static final EmptyIterator INSTANCE = new EmptyIterator();
    /**
     * Default constructor
     */
    private EmptyIterator() {
      // Nothing
    }
    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
      return false;
    }
    /**
     * @see java.util.Iterator#next()
     */
    public Object next() {
      throw new IllegalArgumentException("Empty iterator"); //$NON-NLS-1$
    }
    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
      throw new IllegalArgumentException("Empty iterator"); //$NON-NLS-1$
    }
  }
  
  
  /**
   * Return whether the two given objects are equal
   * @param o1_p a possibly null object
   * @param o2_p a possibly null object
   */
  public static boolean areEqual(Object o1_p, Object o2_p) {
    return o1_p == null && o2_p == null ||
        o1_p != null && o1_p.equals(o2_p);
  }
  
  /**
   * Create and return a constant List from the given objects.
   * Null values are preserved.
   * @param elements_p a non-null array or succession of objects
   * @return a non-null list
   */
  public static <T> List<T> constantList(T... elements_p) {
    // This one is trivial, it is here just for location and name consistency
    return Collections.unmodifiableList(Arrays.asList(elements_p));
  }
  
  /**
   * Create and return a constant Map from the given couples.
   * The order of the keys is preserved.
   * Null objects are forbidden and checked at couple instantiation time.
   * Null couples and duplicate keys are forbidden and checked at map
   * instantiation time.
   * @param couples_p a non-null array or succession of objects
   */
  public static <K, V> Map<K, V> constantMap(
      Tuple2<? extends K, ? extends V>... couples_p) {
    Map<K, V> result = new LinkedHashMap<K, V>();
    for (Tuple2<? extends K, ? extends V> couple : couples_p) {
      if (couple == null)
        throw new IllegalArgumentException("Null couple in constant map declaration"); //$NON-NLS-1$
      V former = result.put(couple.get1(), couple.get2());
      if (former != null)
        throw new IllegalArgumentException(
            "Duplicate key in constant map declaration: " + couple.get1()); //$NON-NLS-1$
    }
    return Collections.unmodifiableMap(result);
  }
  
  /**
   * Create and return a constant Set from the given objects.
   * The order of the objects is preserved modulo duplicates.
   * Null values are removed.
   * @param elements_p a non-null array or succession of objects
   * @return a non-null list
   */
  public static <T> Set<T> constantSet(T... elements_p) {
    Set<T> result = new LinkedHashSet<T>(Arrays.asList(elements_p));
    result.remove(null);
    return Collections.unmodifiableSet(result);
  }
  
  /**
   * Return an iterator over the (virtual) empty set
   * @return a non-null iterator
   */
  @SuppressWarnings("unchecked")
  public static <T> Iterator<T> emptyIterator() {
    return (Iterator<T>)EmptyIterator.INSTANCE;
  }
  
  /**
   * Return an iterator over a singleton
   * @param object_p the element in the singleton
   * @return a non-null iterator
   */
  public static <T> Iterator<T> singletonIterator(T object_p) {
    return Collections.singleton(object_p).iterator();
  }
  
}
