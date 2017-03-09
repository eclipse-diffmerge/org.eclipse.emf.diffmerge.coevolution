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

import org.eclipse.emf.diffmerge.bridge.api.ISymbolProvider;
import org.eclipse.emf.diffmerge.bridge.mapping.Messages;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQuery;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;
import org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage;

/**
 * A basic logging message for incremental bridges.
 */
public class RuleLoggingMessage extends AbstractLoggingMessage {

  /**
   * The transformation step
   */
  public static enum Step {
    /**
     * First iteration: target objects are created.
     */
    TargetCreation,
    /**
     * Second iteration: target objects features are populated.
     */
    TargetDefinition
  }
  
  /**
   * The (possibly null) execution step where this logging message is instantiated
   */
  private Step _executionStep;
  
  /**
   * The (possibly-null) query execution
   */
  private IQueryExecution _queryExection;
  
  /**
   * Default constructor
   * 
   * @param provider_p (non-null) symbol provider
   */
  public RuleLoggingMessage(ISymbolProvider provider_p) {
    super(provider_p);
  }

  /**
   * Constructor with additional information as the execution step.
   * 
   * @param provider_p the (non-null) rule object
   * @param step_p the (non-null) bridge execution step
   */
  public RuleLoggingMessage(ISymbolProvider provider_p, Step step_p) {
    this(provider_p);
    _executionStep = step_p;
  }
  
  /**
   * Constructor with additional information as the execution step.
   * 
   * @param provider_p the (non-null) rule object
   * @param step_p the (non-null) bridge execution step
   */
  public RuleLoggingMessage(ISymbolProvider provider_p, Step step_p, IQueryExecution queryExection_p) {
    this(provider_p, step_p);
    _queryExection = queryExection_p;
  }
  /**
   * Returns the rule object
   * 
   * @return the (non-null) rule object
   */
  protected Object getRuleObject() {
    return getObjectToLabel().keySet().iterator().next();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getPrefix()
   */
  @Override
  protected String getPrefix() {
    if (getExecutionStep() != null && getExecutionStep() == Step.TargetCreation) {
      StringBuilder builder = new StringBuilder();
      builder.append("\t|"); //$NON-NLS-1$
      int depth = 1;
      if (_queryExection!=null) {
        Object rule = getRuleObject();
        if (rule instanceof IRule<?, ?>) {
          IQuery<?, ?> inputProvider = ((IRule<?, ?>) rule).getInputProvider();
          if (inputProvider!=null) {
            depth = _queryExection.getAll().size();
          }
        }
      }
      for (int i=0;i<depth;i++) {
        builder.append("\t|"); //$NON-NLS-1$
      }
      builder.append("  |____Rule ");//$NON-NLS-1$
      return builder.toString();
    }
    return "\t|__Rule "; //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getAdditionalInfo()
   */
  @Override
  protected String getAdditionalInfo() {
    if (getExecutionStep() != null && getExecutionStep() == Step.TargetCreation)
      return Messages.BridgeLogger_TargetCreationMessageSuffix; 
    return Messages.BridgeLogger_TargetDefinitionMessageSuffix; 
  }

  /**
   * @see org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage#getMessageBody()
   */
  @Override
  protected String getMessageBody() {
    StringBuilder builder = new StringBuilder();
    //only one object (the rule) is mapped in rule logging messages
    Object rule = getRuleObject();      
    builder.append("[").append(getRuleName(rule)).append("]"); //$NON-NLS-1$ //$NON-NLS-2$
    return builder.toString();
  }

  /**
   * Returns a user friendly name for the rule given as input.
   * 
   * @param rule_p the (non-null) rule
   * @return the rule name
   */
  protected String getRuleName(Object rule_p) {
    if(rule_p.getClass().isAnonymousClass())
      return rule_p.getClass().getEnclosingClass().getSimpleName();
    return rule_p.getClass().getSimpleName();
  }

  /**
   * Returns a possibly null execution step
   * 
   * @return execution step
   */
  protected Step getExecutionStep() {
    return _executionStep;
  }
}