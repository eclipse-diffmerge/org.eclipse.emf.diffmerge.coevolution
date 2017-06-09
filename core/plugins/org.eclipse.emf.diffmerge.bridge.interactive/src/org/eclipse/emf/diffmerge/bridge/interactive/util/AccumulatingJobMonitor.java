/**
 * <copyright>
 * 
 * Copyright (c) 2016-2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.interactive.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;


/**
 * Copied and adapted from org.eclipse.jface.operation.AccumulatingProgressMonitor.
 * @see "https://bugs.eclipse.org/bugs/show_bug.cgi?id=445802"
 */
public class AccumulatingJobMonitor extends ProgressMonitorWrapper {
  
  /**
   * A collector for work amounts.
   */
  private class Collector implements Runnable {
    /** The optional name of the subtask */
    private String _subTask;
    /** The positive work amount currently done */
    private double _worked;
    /** A non-null progress monitor */
    private IProgressMonitor _monitor;
    /**
     * Constructor
     * @param subTask_p a non-null string
     * @param work_p a positive double
     * @param monitor_p a non-null monitor
     */
    public Collector(String subTask_p, double work_p, IProgressMonitor monitor_p) {
      _subTask = subTask_p;
      _worked = work_p;
      _monitor = monitor_p;
    }
    /**
     * Add an increment to the work
     * @param workedIncrement_p a positive double
     */
    public void worked(double workedIncrement_p) {
      _worked = _worked + workedIncrement_p;
    }
    /**
     * Set the name of the subtask
     * @param subTaskName_p a potentially null string
     */
    public void subTask(String subTaskName_p) {
      _subTask = subTaskName_p;
    }
    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
      clearCollector(this);
      if (_subTask != null)
        _monitor.subTask(_subTask);
      if (_worked > 0)
        _monitor.internalWorked(_worked);
    }
  }
  
  
  /** The non-null display */
  protected final Display _display;
  
  /** The (initially null) collector */
  protected Collector _collector;
  
  /** The potentially null name of the current task */
  protected String _currentTask = ""; //$NON-NLS-1$
  
  
  /**
   * Create an accumulating progress monitor wrapping the given one that uses
   * the given display
   * @param monitor_p the actual progress monitor to be wrapped
   * @param display_p the SWT display used to forward the calls to the wrapped progress
   *                    monitor
   */
  public AccumulatingJobMonitor(IProgressMonitor monitor_p, Display display_p) {
    super(monitor_p);
    Assert.isNotNull(display_p);
    _display = display_p;
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#beginTask(java.lang.String, int)
   */
  @Override
  public void beginTask(final String name_p, final int totalWork_p) {
    synchronized (this) {
      _collector = null;
    }
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        _currentTask = name_p;
        getWrappedProgressMonitor().beginTask(name_p, totalWork_p);
      }
    });
  }
  
  /**
   * Clear the collector object used to accumulate work and subtask calls if it
   * matches the given one
   * @param collectorToClear_p a potentially null collector
   */
  protected synchronized void clearCollector(Collector collectorToClear_p) {
    // Check if the accumulator is still using the given collector.
    // If not, don't clear it.
    if (_collector == collectorToClear_p) {
      _collector = null;
    }
  }
  
  /**
   * Create a collector object to accumulate work and subtask calls
   * @param subTask_p
   * @param work_p
   */
  protected void createCollector(String subTask_p, double work_p) {
    _collector = new Collector(subTask_p, work_p, getWrappedProgressMonitor());
    _display.asyncExec(_collector);
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#done()
   */
  @Override
  public void done() {
    synchronized (this) {
      _collector = null;
    }
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        getWrappedProgressMonitor().done();
      }
    });
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#internalWorked(double)
   */
  @Override
  public synchronized void internalWorked(final double work_p) {
    if (_collector == null) {
      createCollector(null, work_p);
    } else {
      _collector.worked(work_p);
    }
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#setTaskName(java.lang.String)
   */
  @Override
  public void setTaskName(final String name_p) {
    synchronized (this) {
      _collector = null;
    }
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        _currentTask = name_p;
        getWrappedProgressMonitor().setTaskName(name_p);
      }
    });
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#subTask(java.lang.String)
   */
  @Override
  public synchronized void subTask(final String name_p) {
    if (_collector == null) {
      createCollector(name_p, 0);
    } else {
      _collector.subTask(name_p);
    }
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#worked(int)
   */
  @Override
  public synchronized void worked(int work_p) {
    internalWorked(work_p);
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#clearBlocked()
   */
  @Override
  public void clearBlocked() {
    // If this is a monitor that can report blocking do so.
    // Don't bother with a collector as this should only ever
    // happen once and prevent any more progress.
    final IProgressMonitor pm = getWrappedProgressMonitor();
    if (!(pm instanceof IProgressMonitorWithBlocking)) {
      return;
    }
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        ((IProgressMonitorWithBlocking) pm).clearBlocked();
        Dialog.getBlockedHandler().clearBlocked();
      }
    });
  }
  
  /**
   * @see org.eclipse.core.runtime.ProgressMonitorWrapper#setBlocked(org.eclipse.core.runtime.IStatus)
   */
  @Override
  public void setBlocked(final IStatus reason_p) {
    // If this is a monitor that can report blocking do so.
    // Don't bother with a collector as this should only ever
    // happen once and prevent any more progress.
    final IProgressMonitor pm = getWrappedProgressMonitor();
    if (!(pm instanceof IProgressMonitorWithBlocking)) {
      return;
    }
    _display.asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        ((IProgressMonitorWithBlocking) pm).setBlocked(reason_p);
        // Do not give a shell as we want it to block until it opens.
        Dialog.getBlockedHandler().showBlocked(pm, reason_p, _currentTask);
      }
    });
  }
  
}
