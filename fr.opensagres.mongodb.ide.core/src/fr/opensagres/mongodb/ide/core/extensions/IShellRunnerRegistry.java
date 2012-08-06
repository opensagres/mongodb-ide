/*******************************************************************************
 * Copyright (C) 2012 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package fr.opensagres.mongodb.ide.core.extensions;

import java.util.Collection;

/**
 * Shell runner registry.
 * 
 */
public interface IShellRunnerRegistry {

	/**
	 * Return the {@link IShellRunnerType} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	IShellRunnerType getRunnerType(String id);

	/**
	 * Return the list of the {@link IShellRunnerType}.
	 * 
	 * @return
	 */
	Collection<IShellRunnerType> getRunners();
}
