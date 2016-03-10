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
 * A data provider.
 * A data provider is characterized by the type of its output data.
 * It may accept compatible data consumers via methods such as:
 * void accept(ISpecificCompatibleDataConsumer<? super O> consumer_p)
 * @param <O> the type of the output
 * @author Olivier Constant
 */
public interface IDataProvider<O> {
  // Nothing needed
}
