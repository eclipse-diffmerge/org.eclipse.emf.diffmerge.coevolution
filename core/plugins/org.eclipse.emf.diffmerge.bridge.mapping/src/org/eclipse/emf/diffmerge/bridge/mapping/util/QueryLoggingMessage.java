/**
 * <copyright>
 * 
 * Copyright (c) 2017-2018 Thales Global Services S.A.S.
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.diffmerge.bridge.impl.AbstractNamedElement;
import org.eclipse.emf.diffmerge.bridge.impl.emf.EMFSymbolFunction;
import org.eclipse.emf.diffmerge.bridge.mapping.Messages;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryIdentifier;
import org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage;
import org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil;
import org.eclipse.emf.diffmerge.bridge.util.structures.IPureStructure;
import org.eclipse.emf.ecore.EObject;


/**
 * A logging message for queries in Mapping bridges.
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
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getAdditionalInfo()
   */
  @Override
  protected String getAdditionalInfo() {
    return ""; //$NON-NLS-1$
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    IQueryIdentifier<?> queryIdentifier = getQueryIdentifier();
    if (queryIdentifier == null)
      return Messages.BridgeLogger_EmptyQueryExecutionError;
    Object queryResult = getQueryResult();
    if (queryResult == null)
      return Messages.BridgeLogger_EmptyQueryResultError;
    StringBuilder builder = new StringBuilder("["); //$NON-NLS-1$
    String queryName = getQueryName(queryIdentifier);
    builder.append(queryName);
    builder.append("] returns {"); //$NON-NLS-1$
    if (queryResult instanceof EObject) {
      serializeObject(queryResult, builder);
    } else if (queryResult instanceof IPureStructure<?>) {
      Collection<?> contents = ((IPureStructure<?>) queryResult).asCollection();
      for (Object object: contents) {
        serializeObject(object, builder);
        builder.append(", "); //$NON-NLS-1$
      }
    }
    builder.append("}"); //$NON-NLS-1$
    return builder.toString();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getObjects()
   */
  @Override
  public Collection<?> getObjects() {
    Object qResult = getQueryResult();
    Collection<?> result = (qResult == null)?
        Collections.emptySet(): CollectionsUtil.flatten(qResult);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    StringBuilder builder = new StringBuilder();
    int depth = ((IQueryExecution) getQueryObject()).getAll().size();
    builder.append("\t|"); //$NON-NLS-1$
    for (int i=1; i<depth; i++)
      builder.append("\t|"); //$NON-NLS-1$
    builder.append("__Query "); //$NON-NLS-1$
    return builder.toString();
  }
  
  /**
   * Return the latest query identifier in the query execution stack
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
   * Return a user-friendly name for the query identifier given as input
   * @param queryIdentifier_p the (non-null) query identifier
   * @return the query name
   */
  protected String getQueryName(IQueryIdentifier<?> queryIdentifier_p) {
    String queryName = null;
    if (queryIdentifier_p instanceof AbstractNamedElement)
      queryName = ((AbstractNamedElement)queryIdentifier_p).getName();
    else
      queryName = queryIdentifier_p.toString();
    return queryName;
  }
  
  /**
   * Return the query execution backed by this message
   * @return the (non-null) query execution
   */
  protected Object getQueryObject() {
    //the query object is the first object in the map
    return getObjectToLabel().keySet().iterator().next();
  }
  
  /**
   * Return the query result
   * @return the (possibly-null) query result
   */
  public Object getQueryResult() {
    if (getQueryIdentifier() != null)
      return ((IQueryExecution) getQueryObject()).get(getQueryIdentifier());
    return null;
  }
  
  /**
   * Serialize an object in the form (type "name" [identifier]) in the given string builder
   * @param object_p the non-null object to serialize
   * @param builder_p the non-null string builder
   */
  protected void serializeObject(Object object_p, StringBuilder builder_p) {
    EMFSymbolFunction function = EMFSymbolFunction.getInstance();
    String typeName = null;
    if (object_p instanceof EObject)
      typeName = ((EObject) object_p).eClass().getName();
    else
      typeName = object_p.getClass().getSimpleName();
    builder_p.append("("); //$NON-NLS-1$
    builder_p.append(typeName).append(" \""); //$NON-NLS-1$
    builder_p.append(getObjectLabel(object_p)).append("\""); //$NON-NLS-1$
    builder_p.append("[").append(function.getSymbol(object_p)).append("])"); //$NON-NLS-1$//$NON-NLS-2$
  }
  
}