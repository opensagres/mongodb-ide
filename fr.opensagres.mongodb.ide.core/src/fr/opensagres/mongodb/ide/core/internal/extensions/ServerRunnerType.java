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
package fr.opensagres.mongodb.ide.core.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.IServerRunner;
import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerType;

/**
 * {@link IServerRunnerType} implementation.
 * 
 */
public class ServerRunnerType implements IServerRunnerType {

	private final String id;
	private final String startName;
	private final String stopName;
	private final String startDescription;
	private final String stopDescription;
	private final IServerRunner runner;

	public ServerRunnerType(String id, String startName, String stopName,
			String startDescription, String stopDescription,
			IServerRunner runner) {
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

	public IServerRunner getRunner() {
		return runner;
	}
}
