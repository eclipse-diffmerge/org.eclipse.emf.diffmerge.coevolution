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
package org.eclipse.emf.diffmerge.bridge.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
   * Return an iterator over an empty set
   * @return a non-null iterator
   */
  public static <T> Iterator<T> emptyIterator() {
    return Collections.<T>emptySet().iterator();
  }
  
  /**
   * Return an ordered set that contains all atomic elements which are
   * recursively contained by the given object via the Iterable interface.
   * Cycles are supported.
   * @param object_p a non-null object
   * @return a non-null, non-empty ordered set
   */
  public static Collection<Object> flatten(Object object_p) {
    Collection<Object> result = new LinkedHashSet<Object>();
    LinkedList<Object> toExplore = new LinkedList<Object>();
    Collection<Object> explored = new HashSet<Object>();
    toExplore.add(object_p);
    while (!toExplore.isEmpty()) {
      Object current = toExplore.removeFirst();
      explored.add(current);
      if (current instanceof Iterable<?>) {
        for (Object currentPart : ((Iterable<?>)current)) {
          if (!explored.contains(currentPart))
            toExplore.add(currentPart);
        }
      } else {
        result.add(current);
      }
    }
    return Collections.unmodifiableCollection(result);
  }
  
  /**
   * Find and return all elements of the given type in the flattened variant of
   * the given object, where flattening is defined as in flatten(Object).
   * If an element conforming to the given type is found that is also an Iterable,
   * then priority is given to returning the element rather than further decomposing
   * it, except if the given type is Object. In this case, all non-Iterable elements
   * are returned.
   * @param object_p a non-null object
   * @param type_p a non-null type
   * @return a potentially null object
   */
  public static <T> Collection<T> flattenFindAll(Object object_p, Class<T> type_p) {
    Collection<T> result = new LinkedHashSet<T>();
    LinkedList<Object> toExplore = new LinkedList<Object>();
    Collection<Object> explored = new HashSet<Object>();
    toExplore.add(object_p);
    boolean typeIsObject = type_p.equals(Object.class);
    while (!toExplore.isEmpty()) {
      Object current = toExplore.removeFirst();
      boolean isIterable = current instanceof Iterable<?>;
      if (type_p.isInstance(current) && (!typeIsObject || !isIterable)) {
        @SuppressWarnings("unchecked") // OK because of check above
        T elected = (T)current;
        result.add(elected);
        isIterable = false; // Consider atomic since it will be returned
      }
      explored.add(current);
      if (isIterable) {
        for (Object currentPart : ((Iterable<?>)current)) {
          if (!explored.contains(currentPart))
            toExplore.add(currentPart);
        }
      }
    }
    return result;
  }
  
  /**
   * Find and return the first element, if any, of the given type in the flattened
   * variant of the given object, where flattening is defined as in flatten(Object).
   * If an element conforming to the given type is found that is also an Iterable,
   * then priority is given to returning the element rather than further decomposing
   * it, except if the given type is Object. In this case, the first non-Iterable
   * element is returned.
   * @param object_p a non-null object
   * @param type_p a non-null type
   * @return a potentially null object
   */
  public static <T> T flattenFindOne(Object object_p, Class<T> type_p) {
    LinkedList<Object> toExplore = new LinkedList<Object>();
    Collection<Object> explored = new HashSet<Object>();
    toExplore.add(object_p);
    boolean typeIsObject = type_p.equals(Object.class);
    while (!toExplore.isEmpty()) {
      Object current = toExplore.removeFirst();
      boolean isIterable = current instanceof Iterable<?>;
      if (type_p.isInstance(current) && (!typeIsObject || !isIterable)) {
        @SuppressWarnings("unchecked") // OK because of check above
        T result = (T)current;
        return result;
      }
      explored.add(current);
      if (isIterable) {
        for (Object currentPart : ((Iterable<?>)current)) {
          if (!explored.contains(currentPart))
            toExplore.add(currentPart);
        }
      }
    }
    return null;
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
