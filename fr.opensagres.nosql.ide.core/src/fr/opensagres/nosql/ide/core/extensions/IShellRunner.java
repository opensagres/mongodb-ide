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
package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.IDatabase;

/**
 * Shell Runner is used to implement the start/stop of the Mongo Shell Console.
 * 
 */
public interface IShellRunner {

	/**
	 * Start the Mongo Shell Console for the given database.
	 * 
	 * @param database
	 */
	void startShell(IDatabase database) throws Exception;

	/**
	 * Stop the Mongo Shell Console for the given database.
	 * 
	 * @param database
	 */
	void stopShell(IDatabase database) throws Exception;

	/**
	 * Return true if the given database can support this shell runner and false
	 * otherwise.
	 * 
	 * @param database
	 * @return
	 */
	boolean canSupport(IDatabase database);

	/**
	 * Returns true if the shell runner support stop feature and false
	 * otherwise.
	 * 
	 * @return
	 */
	boolean canSupportStop();

}
