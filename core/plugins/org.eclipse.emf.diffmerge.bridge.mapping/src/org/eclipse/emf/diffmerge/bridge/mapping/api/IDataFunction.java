/**
 * <copyright>
 * 
 * Copyright (c) 2014 Thales Global Services S.A.S.
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
 * A data function.
 * A data function is characterized by a type of input data and a type of output data.
 * @param <I> the type of the input
 * @param <O> the type of the output
 * @author Olivier Constant
 */
public interface IDataFunction<I, O> extends IDataConsumer<I>, IDataProvider<O> {
  // Nothing needed
}
