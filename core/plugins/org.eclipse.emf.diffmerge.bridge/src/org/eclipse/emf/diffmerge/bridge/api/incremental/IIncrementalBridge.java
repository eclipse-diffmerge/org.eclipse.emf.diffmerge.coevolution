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
package org.eclipse.emf.diffmerge.bridge.api.incremental;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.diffmerge.bridge.api.IBridge;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution;
import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;


/**
 * An incremental bridge between two data sets.
 * Incrementality is achieved by creating an intermediate target data set which is
 * populated according to the source data set and then merged with the actual target
 * data set.
 * @param <SD> the type of the source data set
 * @param <TD> the type of the target data set
 * @param <ID> the type of the intermediate target data set
 * @author Olivier Constant
 */
public interface IIncrementalBridge<SD, TD, ID> extends IBridge<SD, TD> {
  
  /**
   * Create and return an empty intermediate data set for the given source and target data sets
   * @param sourceDataSet_p a non-null data set
   * @param targetDataSet_p a non-null data set
   * @return a non-null object
   */
  ID createIntermediateDataSet(SD sourceDataSet_p, TD targetDataSet_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridge#createExecution(org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace.Editable)
   */
  IIncrementalBridgeExecution.Editable createExecution(IBridgeTrace.Editable trace_p);
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.IBridge#executeOn(java.lang.Object, java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.IBridgeExecution, org.eclipse.core.runtime.IProgressMonitor)
   */
  IIncrementalBridgeExecution executeOn(SD sourceDataSet_p, TD targetDataSet_p,
      IBridgeExecution execution_p, IProgressMonitor monitor_p);
  
  /**
   * Execute the bridge between the given data sets incrementally based on the given former trace
   * @param sourceDataSet_p a non-null data set
   * @param targetDataSet_p a non-null data set
   * @param execution_p an optional execution as context, or null
   * @param referenceTrace_p an optional trace of the former execution
   * @param deferInteractiveMerge_p whether the interactive merge phase must be deferred if any
   * @param monitor_p an optional progress monitor
   * @return a non-null execution that results from the operation call, which may be equal to execution_p
   */
  IIncrementalBridgeExecution executeOn(SD sourceDataSet_p, TD targetDataSet_p,
      IBridgeExecution execution_p, IBridgeTrace referenceTrace_p, boolean deferInteractiveMerge_p,
      IProgressMonitor monitor_p);
  
  /**
   * Return whether the given target data set can be considered empty
   * @param dataSet_p a non-null object
   */
  boolean isEmpty(TD dataSet_p);
  
  /**
   * Merge the given existing data set with the given created one so as
   * to achieve updates, based on the given bridge execution
   * @param created_p a non-null object
   * @param existing_p a non-null object
   * @param execution_p a non-null object
   * @param monitor_p an optional progress monitor
   * @return a non-null status
   */
  IStatus merge(ID created_p, TD existing_p, IIncrementalBridgeExecution execution_p,
      IProgressMonitor monitor_p);
  
  /**
   * Perform the interactive merge step of the bridge process so as to achieve an update of
   * the target data set, based on the given bridge execution.
   * Implementers cannot assume that the current thread is the UI thread.
   * @param execution_p a non-null object
   * @param monitor_p an optional progress monitor
   * @return a non-null status
   */
  IStatus mergeInteractively(IIncrementalBridgeExecution execution_p, IProgressMonitor monitor_p);
  
  
  /**
   * An incremental bridge whose non-incremental transformation is defined by another bridge.
   */
  interface Wrapping<SD, TD, ID> extends IIncrementalBridge<SD, TD, ID> {
    /**
     * Return the non-incremental bridge that carries out the transformation
     * @return a non-null object
     */
    IBridge<? super SD, ? super TD> getTransformationBridge();
  }
  
}
