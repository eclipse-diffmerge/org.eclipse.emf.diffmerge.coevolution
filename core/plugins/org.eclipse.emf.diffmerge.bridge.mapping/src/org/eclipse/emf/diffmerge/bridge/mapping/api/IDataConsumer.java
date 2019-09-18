/*********************************************************************
 * Copyright (c) 2014-2019 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 **********************************************************************/
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
