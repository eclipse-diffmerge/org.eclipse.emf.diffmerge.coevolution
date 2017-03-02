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

import org.eclipse.emf.diffmerge.bridge.api.ICause;

/**
 * A basic logging message for incremental bridges.
 */
public class BaseTraceLoggingMessage extends AbstractLoggingMessage {
  
  /**
   * The cause of the presence of the target element
   */
  protected final ICause.Symbolic<?,?> _cause;
  
  /**
   * Default constructor
   * 
   * @param target_p the (non-null) target object
   * @param cause_p the (non-null) cause
   */
  public BaseTraceLoggingMessage(Object target_p, ICause.Symbolic<?,?> cause_p) {
    super(target_p);
    _cause = cause_p;
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    StringBuilder builder = new StringBuilder();
    builder.append("Produced [" + getObjectLabel(getTarget()) + "]");  //$NON-NLS-1$//$NON-NLS-2$
    builder.append(" From ["); //$NON-NLS-1$
    //we are agnostic of the cause here, navigable causes are in mapping
    builder.append(getObjectLabel(getCause()));
    builder.append("]"); //$NON-NLS-1$
    return builder.toString();
  }

  /**
   * Returns the target object backed by this message, it is the first object
   * added to the map.
   * 
   * @return the (non-null) target object
   */
  public Object getTarget() {
    return getObjectToLabel().keySet().iterator().next();
  }

  /**
   * Returns the cause of the presence of the target object
   * 
   * @return the (non-null) cause
   */
  public ICause.Symbolic<?, ?> getCause() {
    return _cause;
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    return ""; //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getAdditionalInfo()
   */
  @Override
  protected String getAdditionalInfo() {
    return ""; //$NON-NLS-1$
  }
}