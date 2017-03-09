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
package org.eclipse.emf.diffmerge.bridge.mapping.util;

import org.eclipse.emf.diffmerge.bridge.api.ICause.Symbolic;
import org.eclipse.emf.diffmerge.bridge.impl.emf.EMFSymbolFunction;
import org.eclipse.emf.diffmerge.bridge.util.BaseTraceLoggingMessage;
import org.eclipse.emf.ecore.EObject;

/**
 * A trace logging message where objects are serialized using symbol functions.
 */
public class TraceLoggingMessage extends BaseTraceLoggingMessage {

  /**
   * Default constructor
   * 
   * @param target_p the (non-null) target object
   * @param cause_p the (non-null) presence cause
   */
  public TraceLoggingMessage(Object target_p, Symbolic<?, ?> cause_p) {
    super(target_p, cause_p);
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    return "\t|\t|__Produced "; //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    StringBuilder builder = new StringBuilder("("); //$NON-NLS-1$
    builder.append(getTarget().getClass().getSimpleName()).append(" \""); //$NON-NLS-1$
    builder.append(getObjectLabel(getTarget())).append("\""); //$NON-NLS-1$
    final EMFSymbolFunction function = EMFSymbolFunction.getInstance();
    //append the identifier inside the message using symbol function. 
    builder.append("[").append(function.getSymbol(getTarget())).append("]");//$NON-NLS-1$ //$NON-NLS-2$
    //source however can be a single object or a tuple
    builder.append(") From {"); //$NON-NLS-1$
    for (Object source : getCause().getSourceElements()) {
      String sourceName = getObjectLabel(source);
      String sourceType = ((EObject) source).eClass().getName();
      builder.append("("); //$NON-NLS-1$
      builder.append(sourceType).append(" \""); //$NON-NLS-1$
      builder.append(sourceName).append("\""); //$NON-NLS-1$
      builder.append("[").append(function.getSymbol(source)).append("])");//$NON-NLS-1$ //$NON-NLS-2$
    }
    builder.append("}"); //$NON-NLS-1$
    return builder.toString();
  }
}