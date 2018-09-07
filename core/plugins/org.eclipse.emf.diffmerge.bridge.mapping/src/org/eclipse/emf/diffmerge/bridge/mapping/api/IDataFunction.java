/*********************************************************************
 * Copyright (c) 2014-2018 Thales Global Services S.A.S.
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
 * A data function.
 * A data function is characterized by a type of input data and a type of output data.
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author Olivier Constant
 */
public interface IDataFunction<I, O> extends IDataConsumer<I>, IDataProvider<O> {
  // Nothing needed
}
