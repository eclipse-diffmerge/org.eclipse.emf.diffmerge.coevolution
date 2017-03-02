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

import java.util.List;

import org.eclipse.emf.diffmerge.bridge.impl.emf.EMFSymbolFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.Messages;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.impl.QueryIdentifier;
import org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage;
import org.eclipse.emf.ecore.EObject;

/**
 * A basic logging message for incremental bridges.
 */
public class QueryLoggingMessage extends AbstractLoggingMessage{

  /**
   * Default constructor. This logging message uses the query execution stack to
   * compute the message to display. Do not call it with an empty query
   * execution.
   */
  public QueryLoggingMessage(IQueryExecution queryExecution_p) {
    super(queryExecution_p);
  }

  /**
   * Returns the query execution backed by this message
   * @return the (non-null) query execution
   */
  protected Object getQueryObject() {
    //only one object (the query) is mapped in query logging messages
    return getObjectToLabel().keySet().iterator().next();
  }
  
  /**
   * Returns the query result
   * 
   * @return the (possibly-null) query result
   */
  public Object getQueryResult() {
    if (getQueryIdentifier()!=null)
      return ((IQueryExecution) getQueryObject()).get(getQueryIdentifier());
    return null;
  }

  /**
   * Returns the latest query identifier in the query execution stack.
   * 
   * @return the (possibly-null) query identifier
   */
  public IQueryIdentifier<?> getQueryIdentifier() {
    List<? extends IQueryIdentifier<?>> queryIdentifiers =  ((IQueryExecution) getQueryObject()).getQueryIdentifiers();
    if (queryIdentifiers.size()==0) {
      //this logging message has been created before the first query has been executed.
      return null;
    }
    return queryIdentifiers.get(queryIdentifiers.size() - 1);
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    if (getQueryIdentifier() == null)
      return Messages.QueryLoggingMessage_EmptyQueryExecutionError;
    if (getQueryResult() == null)
      return Messages.QueryLoggingMessage_EmptyQueryResultError;
    StringBuilder builder = new StringBuilder("["); //$NON-NLS-1$
    String typeName = ((EObject) getQueryResult()).eClass().getName();
    String queryName = ((QueryIdentifier<?>)getQueryIdentifier()).getName();
    builder.append(queryName);
    builder.append("] returns ("); //$NON-NLS-1$
    EMFSymbolFunction function = EMFSymbolFunction.getInstance();
    builder.append(typeName).append(" \""); //$NON-NLS-1$
    builder.append(getObjectLabel(getQueryResult())).append("\""); //$NON-NLS-1$
    builder.append("[").append(function.getSymbol(getQueryResult())).append("])");  //$NON-NLS-1$//$NON-NLS-2$
    return builder.toString();
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    return "\t|__Query "; //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getAdditionalInfo()
   */
  @Override
  protected String getAdditionalInfo() {
    return ""; //$NON-NLS-1$
  }
}