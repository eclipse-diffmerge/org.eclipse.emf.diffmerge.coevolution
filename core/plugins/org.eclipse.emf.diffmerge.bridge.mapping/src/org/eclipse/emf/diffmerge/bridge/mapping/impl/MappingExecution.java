/**
 * <copyright>
 * 
 * Copyright (c) 2014-2017 Thales Global Services S.A.S.
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
package org.eclipse.emf.diffmerge.bridge.mapping.impl;

import static org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil.flattenFindAll;
import static org.eclipse.emf.diffmerge.bridge.util.CollectionsUtil.flattenFindOne;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.diffmerge.bridge.api.IBridgeTrace;
import org.eclipse.emf.diffmerge.bridge.api.ICause;
import org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeTraceExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingCause;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRule;
import org.eclipse.emf.diffmerge.bridge.mapping.api.IRuleIdentifier;
import org.eclipse.emf.diffmerge.bridge.mapping.util.TraceLoggingMessage;
import org.eclipse.emf.diffmerge.bridge.util.AbstractLoggingMessage;


/**
 * A mapping execution which can be modified.
 * @author Olivier Constant
 */
public class MappingExecution extends AbstractBridgeTraceExecution implements IMappingExecution.Editable {
  
  /** The content of the rule environment: source data -> rule -> (query execution, target data) */
  protected final Map<Object, Map<IRule<?,?>, PendingDefinition>> _content;
  
  /** The map from (used) rule identifiers to rules */
  protected final Map<IRuleIdentifier<?,?>, IRule<?,?>> _ruleMap;
  
  /** Whether duplicate pending definitions are tolerated (only one among the duplicates will be processed) */
  private boolean _isTolerantToDuplicates;
  
  /** The (initially null) target data set */
  private Object _targetDataSet;
  
  
  /**
   * Constructor
   * @param trace_p the optional trace that reflects this execution
   */
  public MappingExecution(IBridgeTrace.Editable trace_p) {
    super(trace_p);
    // LinkedHashMap to preserve rule order
    _content = new LinkedHashMap<Object, Map<IRule<?,?>, PendingDefinition>>();
    _ruleMap = new HashMap<IRuleIdentifier<?,?>, IRule<?,?>>();
    _isTolerantToDuplicates = true;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.api.INavigableBridgeExecution#get(org.eclipse.emf.diffmerge.bridge.api.ICause)
   */
  public Object get(ICause<?> cause_p) {
    Object result = null;
    if (cause_p instanceof IMappingCause<?, ?>) {
      @SuppressWarnings("unchecked")
      IMappingCause<Object, Object> cause = (IMappingCause<Object, Object>)cause_p;
      result = get(cause.getSource(), cause.getRule());
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#get(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IRule)
   */
  public <S, T> T get(S source_p, IRule<S, T> rule_p) {
    return get(source_p, rule_p.getID());
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#get(java.lang.Object, org.eclipse.emf.diffmerge.bridge.mapping.api.IRuleIdentifier)
   */
  @SuppressWarnings("unchecked")
  public <S, T> T get(S source_p, IRuleIdentifier<S, T> ruleID_p) {
    T result = null;
    IRule<?,?> rule = _ruleMap.get(ruleID_p);
    if (rule != null) {
      Map<IRule<?,?>, PendingDefinition> ruleToTarget =
          _content.get(source_p);
      if (ruleToTarget != null) {
        PendingDefinition pendingDef = ruleToTarget.get(rule);
        if (pendingDef != null)
          result = (T)pendingDef.getTarget();
      }
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getAll(java.lang.Object)
   */
  public List<Object> getAll(Object source_p) {
    List<Object> result = Collections.emptyList();
    Map<IRule<?,?>, PendingDefinition> ruleToDef = _content.get(source_p);
    if (ruleToDef != null) {
      result = new ArrayList<Object>(ruleToDef.size());
      for (PendingDefinition pendingDef : ruleToDef.values()) {
        result.add(pendingDef.getTarget());
      }
      result = Collections.unmodifiableList(result);
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getAll(java.lang.Object, java.lang.Class)
   */
  public <T> List<T> getAll(Object source_p, Class<T> type_p) {
    List<T> result = Collections.emptyList();
    Map<IRule<?,?>, PendingDefinition> ruleToDef = _content.get(source_p);
    if (ruleToDef != null) {
      result = new ArrayList<T>();
      for (PendingDefinition pendingDef : ruleToDef.values()) {
        result.addAll(flattenFindAll(pendingDef.getTarget(), type_p));
      }
    }
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getOne(java.lang.Object, java.lang.Class)
   */
  public <T> T getOne(Object source_p, Class<T> type_p) {
    Map<IRule<?,?>, PendingDefinition> ruleToDef = _content.get(source_p);
    if (ruleToDef != null) {
      for (PendingDefinition pendingDef : ruleToDef.values()) {
        T result = flattenFindOne(pendingDef.getTarget(), type_p);
        if (result != null)
          return result;
      }
    }
    return null;
  }
  
  /**
   * Return the pending definitions associated with the given source
   * @param source_p a non-null object
   * @return a possibly null object
   */
  public Map<IRule<?,?>, PendingDefinition> getPendingDefinitions(Object source_p) {
    return _content.get(source_p);
  }
  
  /**
   * Return the set of registered sources in deterministic order
   * @return a non-null, possibly empty set
   */
  public Set<Object> getPendingSources() {
    return _content.keySet();
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getRuleInputs(org.eclipse.emf.diffmerge.bridge.mapping.api.IRule, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public <S> Collection<S> getRuleInputs(IRule<S, ?> rule_p, IQueryExecution context_p) {
    Collection<S> result = getRuleInputs(rule_p.getID(), context_p);
    return result;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getRuleInputs(org.eclipse.emf.diffmerge.bridge.mapping.api.IRuleIdentifier, org.eclipse.emf.diffmerge.bridge.mapping.api.IQueryExecution)
   */
  public <S> Collection<S> getRuleInputs(IRuleIdentifier<S, ?> ruleID_p,
      IQueryExecution context_p) {
    List<S> result = new LinkedList<S>(); //Content uniqueness guaranteed by construction below
    IRule<?,?> rule = _ruleMap.get(ruleID_p);
    if (rule != null) {
      // Focus is not on performance here since we navigate through map entries
      QueryExecution context = (context_p instanceof QueryExecution)? (QueryExecution)context_p: null;
      for (Map.Entry<Object, Map<IRule<?,?>, PendingDefinition>> sourceEntry : _content.entrySet()) {
        PendingDefinition pendingDef = sourceEntry.getValue().get(rule);
        if (pendingDef != null && (context == null ||
            context.isAncestorOrEquals(pendingDef.getQueryExecution()))) {
          @SuppressWarnings("unchecked")
          S source = (S)sourceEntry.getKey();
          result.add(source);
        }
      }
    }
    return result;
  }
  
  /**
   * Return whether the given cause is already registered
   * @param cause_p a potentially null cause
   */
  public boolean isRegistered(ICause<?> cause_p) {
    return get(cause_p) != null;
  }
  
  /**
   * Return whether duplicate pending definitions are tolerated,
   * in which case only one among the duplicates will be processed
   */
  public boolean isTolerantToDuplicates() {
    return _isTolerantToDuplicates;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeExecution#put(org.eclipse.emf.diffmerge.bridge.api.ICause, java.lang.Object)
   */
  @Override
  public void put(ICause<?> cause_p, Object target_p) {
    if (cause_p instanceof IMappingCause<?,?>) {
      IMappingCause<?,?> cause = (IMappingCause<?,?>)cause_p;
      IRule<?,?> rule = cause.getRule();
      // Register rule
      _ruleMap.put(rule.getID(), rule);
      Object source = cause.getSource();
      Map<IRule<?,?>, PendingDefinition> ruleToTarget = _content.get(source);
      if (ruleToTarget == null) {
        // Use LinkedHashMap to preserve order and thus determinism of execution
        ruleToTarget = new LinkedHashMap<IRule<?,?>, PendingDefinition>();
        _content.put(source, ruleToTarget);
      }
      Object squatter = ruleToTarget.put(rule,
          new PendingDefinition(cause.getQueryExecution(), target_p));
      if (squatter != null && !isTolerantToDuplicates())
        throw new IllegalArgumentException(
            String.format(
                "A pending definition is already registered for rule [%1$s] on source [%2$s]: [%3$s] replaced by [%4$s].", //$NON-NLS-1$
                rule, source, squatter, target_p));
    }
  }
  
  /**
   * Set whether duplicate pending definitions are tolerated,
   * in which case only one among the duplicates will be processed
   * @param tolerant_p whether the execution must be tolerant
   */
  public void setTolerantToDuplicates(boolean tolerant_p) {
    _isTolerantToDuplicates = tolerant_p;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.mapping.api.IMappingExecution#getTargetDataSet()
   */
  @SuppressWarnings("unchecked")
  public <TD> TD getTargetDataSet() {  
	  return (TD) _targetDataSet;
  }
  
  /**
   * @see org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeExecution#putInTrace(org.eclipse.emf.diffmerge.bridge.api.ICause, java.lang.Object)
   */
	@Override
	public void putInTrace(ICause<?> cause_p, Object target_p) {
	  // Increases visibility
		super.putInTrace(cause_p, target_p);
	}
	
	/**
	 * @see org.eclipse.emf.diffmerge.bridge.impl.AbstractBridgeExecution#createTraceLoggingMessage(java.lang.Object, org.eclipse.emf.diffmerge.bridge.api.ICause)
	 */
	@Override
	protected AbstractLoggingMessage createTraceLoggingMessage(
	    Object target_p, ICause<?> cause_p) {
	  return new TraceLoggingMessage(target_p, cause_p);
	}
  
  /**
   * Set the target data set of the execution
   * @param targetDataSet_p a non-null target data set
   */
  public void setTargetDataSet(Object targetDataSet_p) {
    _targetDataSet = targetDataSet_p;
  }
  
  /**
   * A trivial structure for pending definitions, associating a target with the query execution
   * in which it is defined.
   */
  public static class PendingDefinition {
    /** The non-null query execution */
    private IQueryExecution _context;
    /** The non-null target object */
    private Object _target;
    /**
     * Constructor
     * @param context_p the non-null query execution in which the target object is defined
     * @param target_p the non-null target object
     */
    protected PendingDefinition(IQueryExecution context_p, Object target_p) {
      if (context_p == null || target_p == null)
        throw new IllegalArgumentException("Null value in pending definition"); //$NON-NLS-1$
      _context = context_p;
      _target = target_p;
    }
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override public boolean equals(Object other_p) {
      boolean result = false;
      if (other_p instanceof PendingDefinition) {
        PendingDefinition peer = (PendingDefinition)other_p;
        result =
          getQueryExecution().equals(peer.getQueryExecution()) &&
          getTarget().equals(peer.getTarget());
      }
      return result; 
    }
    /**
     * Return the query execution
     * @return a non-null object
     */
    public IQueryExecution getQueryExecution() { return _context; }
    /**
     * Return the target object
     * @return a non-null object
     */
    public Object getTarget() { return _target; }
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getQueryExecution().hashCode();
      result = prime * result + getTarget().hashCode();
      return result;
    }
  }
  
}
