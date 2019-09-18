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
package org.eclipse.emf.diffmerge.bridge.api;

import org.eclipse.core.runtime.IProgressMonitor;


/**
 * An executable bridge from a source data set to a target data set.
 * As a result of its execution, a bridge maps target data elements to the corresponding
 * cause of their presence in the source data set, via a trace.
 * Under these sole assumptions, a bridge can be made incremental.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @author Olivier Constant
 */
public interface IBridge<SD, TD> {
  
  /**
   * Create and return a new execution for the bridge based on the given trace
   * @param trace_p an optional trace in which to record
   * @return a non-null object
   */
  IBridgeExecution.Editable createExecution(IBridgeTrace.Editable trace_p);
  
  /**
   * Execute the bridge between the given data sets
   * @param sourceDataSet_p a non-null data set
   * @param targetDataSet_p a non-null data set
   * @param execution_p an optional execution as context, or null
   * @param monitor_p an optional progress monitor
   * @return a non-null execution that results from the operation call, which may be equal to execution_p
   */
  IBridgeExecution executeOn(SD sourceDataSet_p, TD targetDataSet_p,
      IBridgeExecution execution_p, IProgressMonitor monitor_p);
  
  /**
   * Return the total amount of work needed for the bridge execution on the
   * given data sets for usage by a progress monitor
   * @param sourceDataSet_p a non-null data set
   * @param targetDataSet_p a non-null data set
   * @return a positive int
   */
  int getWorkAmount(SD sourceDataSet_p, TD targetDataSet_p);
  
}
