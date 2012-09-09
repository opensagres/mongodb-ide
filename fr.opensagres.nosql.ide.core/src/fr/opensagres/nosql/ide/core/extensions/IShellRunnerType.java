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

/**
 * Describes a Shell runner {@link IShellRunner}.
 * 
 */
public interface IShellRunnerType {

	/**
	 * Return the identifier of the runtime.
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Return the start name of the shell runner.
	 * 
	 * @return
	 */
	String getStartName();

	/**
	 * Return the stop name of the shell runner.
	 * 
	 * @return
	 */
	String getStopName();

	/**
	 * Return the start description of the shell runner.
	 * 
	 * @return
	 */
	String getStartDescription();

	/**
	 * Return the start description of the shell runner.
	 * 
	 * @return
	 */
	String getStopDescription();

	/**
	 * Return the instance of the shell runner.
	 * 
	 * @return
	 */
	IShellRunner getRunner();

}
