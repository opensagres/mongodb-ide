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
package fr.opensagres.nosql.ide.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.IShellRunner;
import fr.opensagres.nosql.ide.core.extensions.IShellRunnerType;

/**
 * {@link IShellRunnerType} implementation.
 * 
 */
public class ShellRunnerType implements IShellRunnerType {

	private final String id;
	private final String startName;
	private final String stopName;
	private final String startDescription;
	private final String stopDescription;
	private final IShellRunner runner;

	public ShellRunnerType(String id, String startName, String stopName,
			String startDescription, String stopDescription, IShellRunner runner) {
		this.id = id;
		this.startName = startName;
		this.stopName = stopName;
		this.startDescription = startDescription;
		this.stopDescription = stopDescription;
		this.runner = runner;
	}

	public String getId() {
		return id;
	}

	public String getStartName() {
		return startName;
	}

	public String getStopName() {
		return stopName;
	}

	public String getStartDescription() {
		return startDescription;
	}

	public String getStopDescription() {
		return stopDescription;
	}

	public IShellRunner getRunner() {
		return runner;
	}
}
