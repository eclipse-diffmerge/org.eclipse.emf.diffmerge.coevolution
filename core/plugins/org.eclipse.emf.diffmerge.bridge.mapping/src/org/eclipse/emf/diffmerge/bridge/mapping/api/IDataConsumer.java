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
package org.eclipse.emf.diffmerge.bridge.mapping.api;


/**
 * A data consumer.
 * A data consumer is characterized by the type of its input data.
 * A data consumer requires a unique data producer to operate.
 * @param <I> the type of the input
 * @author Olivier Constant
 */
public interface IDataConsumer<I> {
  
  /**
   * Return the provider of inputs, if any
   * @return a possibly null object
   */
  IDataProvider<? extends I> getInputProvider();
  
}
