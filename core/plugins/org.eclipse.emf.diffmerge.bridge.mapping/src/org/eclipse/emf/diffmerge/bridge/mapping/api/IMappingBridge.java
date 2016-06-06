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
package org.eclipse.emf.diffmerge.bridge.mapping.api;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;


/**
 * An executable bridge that is made of queries and rules.
 * All query instances in the bridge must have different IDs.
 * All rule instances in the bridge must have different IDs.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @author Olivier Constant
 */
public interface IMappingBridge<SD, TD> extends IBridge<SD, TD>, IQueryHolder<SD> {
  
  /**
   * Add the given target data into the given target data set, if feasible
   * @param targetDataSet_p a non-null object
   * @param target_p a non-null object
   * @return whether the operation succeeded
   */
  boolean addTarget(TD targetDataSet_p, Object target_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridge#executeOn(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  IMappingExecution executeOn(SD sourceDataSet_p, TD targetDataSet_p,
      IBridgeExecution execution_p, IProgressMonitor monitor_p);
  
  /**
   * Return the total number of queries and sub-queries, recursively
   * @return a positive int
   */
  int getNbQueries();
  
  /**
   * Return the total number of rules
   * @return a positive int
   */
  int getNbRules();
  
  /**
   * Notify that all target data elements have been created in the given data set
   */
  void targetsCreated(TD targetDataSet_p);
  
  /**
   * Notify that all target data elements have been defined in the given data set
   */
  void targetsDefined(TD targetDataSet_p);
  
}
