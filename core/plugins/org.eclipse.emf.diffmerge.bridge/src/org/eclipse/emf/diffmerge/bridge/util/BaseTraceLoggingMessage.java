/**
 * <copyright>
 * 
 * Copyright (c) 2017 Thales Global Services S.A.S.
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

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.diffmerge.bridge.api.ICause;


/**
 * A basic logging message for bridge executions.
 */
public class BaseTraceLoggingMessage extends AbstractLoggingMessage {
  
  /** The non-null cause of the presence of a target data element */
  protected final ICause<?> _cause;
  
  
  /**
   * Default constructor
   * @param target_p the (non-null) target data element
   * @param cause_p the (non-null) cause
   */
  public BaseTraceLoggingMessage(Object target_p, ICause<?> cause_p) {
    super(target_p);
    _cause = cause_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getAdditionalInfo()
   */
  @Override
  protected String getAdditionalInfo() {
    return ""; //$NON-NLS-1$
  }
  
  /**
   * Return the cause of the presence of the target object
   * @return the (non-null) cause
   */
  public ICause<?> getCause() {
    return _cause;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    StringBuilder builder = new StringBuilder();
    builder.append("Produced [" + getObjectLabel(getTarget()) + "]");  //$NON-NLS-1$//$NON-NLS-2$
    builder.append(" From ["); //$NON-NLS-1$
    builder.append(getObjectLabel(getCause()));
    builder.append("]"); //$NON-NLS-1$
    return builder.toString();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getObjects()
   */
  @Override
  public Collection<?> getObjects() {
    Collection<?> sourceElements = getCause().getSourceElements();
    Collection<Object> result = new LinkedHashSet<Object>(sourceElements.size() +1);
    result.addAll(sourceElements);
    Object target = getTarget();
    if (target != null)
      result.add(target);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    return ""; //$NON-NLS-1$
  }
  
  /**
   * Return the target object backed by this message; it is the first object
   * added to the map.
   * @return the (non-null) target object
   */
  public Object getTarget() {
    return getObjectToLabel().keySet().iterator().next();
  }
  
}