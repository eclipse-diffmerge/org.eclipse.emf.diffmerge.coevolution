/*********************************************************************
 * Copyright (c) 2016-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.diffmerge.bridge.interactive.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.swt.widgets.Display;


/**
 * Experimental: A progress monitor which accumulates the number of ticks per
 * blocks therefore minimizing the number of overall notifications. This monitor
 * is introduced to deal with jobs which generates big numbers of ticks/sub
 * tasks as in this case the performance of classical progress monitors is very
 * bad.
 * 
 * @see "https://bugs.eclipse.org/bugs/show_bug.cgi?id=445802"
 */
public class BlockProgressMonitor extends ProgressMonitorWrapper {
  
  /**
   * The increment runnable definition.
   */
  private class BlockProgress implements Runnable {
    /** The maximum block size above which the monitor starts the increment runnable */
    private static final int PROGRESS_BLOCK_SIZE = 10;
    /** The current number of cached ticks */
    protected int _ticks;
    /** The non-null internal progress monitor */
    private IProgressMonitor _monitor;
    /** The (initially null) current sub task */
    private String _subTask;
    /**
     * Default constructor
     * @param monitor_p the non-null progress monitor to cache
     * @param worked_p the number of consumed ticks
     */
    public BlockProgress(IProgressMonitor monitor_p, int worked_p) {
      _monitor = monitor_p;
      _ticks = worked_p;
    }
    /**
     * Add additional ticks to the current cached ticks
     * @param additionalWork_p additional ticks
     */
    public void worked(int additionalWork_p) {
      _ticks += additionalWork_p;
    }
    /**
     * Create a subtask
     * @param subTaskName_p the sub task name
     */
    public void subTask(String subTaskName_p) {
      _subTask = subTaskName_p;
    }
    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
      _monitor.subTask(_subTask);
      _monitor.internalWorked(_ticks);
    }
  }
  
  
  /** The non-null display */
  protected final Display _display;
  
  /** The (initially null) increment runnable */
  protected BlockProgress _block;
  
  
  /**
   * Constructor
   * @param monitor_p the non-null original monitor
   * @param display_p a non-null display
   */
  public BlockProgressMonitor(IProgressMonitor monitor_p, Display display_p) {
    super(monitor_p);
    _display = display_p;
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#beginTask(java.lang.String, int)
   */
  @Override
  public void beginTask(final String name_p, final int totalWork_p) {
    _block = new BlockProgress(getWrappedProgressMonitor(), 0);
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        getWrappedProgressMonitor().beginTask(name_p, totalWork_p);
      }
    });
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#subTask(java.lang.String)
   */
  @Override
  public void subTask(final String name_p) {
    if (_block == null)
      _block = new BlockProgress(getWrappedProgressMonitor(), 0);
    synchronized (_block) {
      _block.subTask(name_p);
    }
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#worked(int)
   */
  @Override
  public void worked(int work_p) {
    internalWorked(work_p);
  }
  
  /**
   * Increment the number of ticks, launch the runnable when the number of
   * ticks exceeds the maximum block size
   * @param work_p the number of ticks to increment
   */
  private void internalWorked(final int work_p) {
    _block.worked(work_p);
    synchronized (_block) {
      if (_block._ticks >= BlockProgress.PROGRESS_BLOCK_SIZE) {
        _display.asyncExec(_block);
        _block = null;
      }
    }
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#setTaskName(java.lang.String)
   */
  @Override
  public void setTaskName(final String name_p) {
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        getWrappedProgressMonitor().setTaskName(name_p);
      }
    });
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#done()
   */
  @Override
  public void done() {
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        getWrappedProgressMonitor().done();
      }
    });
    _block = null;
  }
  
}
