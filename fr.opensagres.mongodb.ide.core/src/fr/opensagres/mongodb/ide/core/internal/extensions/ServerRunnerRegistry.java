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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.mongodb.ide.core.extensions.AbstractRegistry;
import fr.opensagres.mongodb.ide.core.extensions.IServerRunner;
import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerRegistry;
import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerType;
import fr.opensagres.mongodb.ide.core.internal.Activator;
import fr.opensagres.mongodb.ide.core.internal.Trace;

/**
 * Registry which holds instance of {@link IServerRunnerType} created by the
 * fr.opensagres.mongodb.ide.core.serverRunners" extension point.
 * 
 */
public class ServerRunnerRegistry extends AbstractRegistry implements
		IServerRunnerRegistry {

	private static final String RUNNER_ELT = "runner";
	public static final String START_NAME_ATTR = "startName";
	public static final String STOP_NAME_ATTR = "stopName";
	public static final String START_DESCRIPTION_ATTR = "startDescription";
	public static final String STOP_DESCRIPTION_ATTR = "stopDescription";

	private static final String SHELL_RUNNER_MANAGER_EXTENSION_POINT = "serverRunners";

	private Map<String, IServerRunnerType> runnerTypes = new HashMap<String, IServerRunnerType>();

	/**
	 * Return the {@link IServerRunnerType} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IServerRunnerType getRunnerType(String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return runnerTypes.get(id);
	}

	/**
	 * Return the list of the {@link IServerRunnerType}.
	 * 
	 * @return
	 */
	public Collection<IServerRunnerType> getRunners() {
		loadRegistryIfNedded();
		return runnerTypes.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseServerRunnerManagers(cf);
		} else {
			// TODO : remove references
		}

	}

	protected synchronized void loadRegistry() {
		if (isRegistryIntialized()) {
			return;
		}
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry != null) {
			IConfigurationElement[] cf = registry.getConfigurationElementsFor(
					getPluginId(), getExtensionPoint());
			parseServerRunnerManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each runner element
	 * an instance of {@link IServerRunnerType}.
	 * 
	 * <pre>
	 * 	  <runner
	 * 	        id="fr.opensagres.mongodb.ide.core.extensions.ConnectServerRunner"
	 * 	        class="fr.opensagres.mongodb.ide.core.internal.extensions.ConnectServerRunner"
	 * 	        startName="%ConnectServerRunner.startName"
	 * 	        stopName="%ConnectServerRunner.stopName"
	 * 	        startDescription="%ConnectServerRunner.startDesc"
	 * 	        stopDescription="%ConnectServerRunner.stopDesc" >
	 * 	  </runner>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseServerRunnerManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			String id = null;
			String startName = null;
			String stopName = null;
			String startDescription = null;
			String stopDescription = null;
			if (RUNNER_ELT.equals(ce.getName())) {
				id = ce.getAttribute(ID_ATTR);
				startName = ce.getAttribute(START_NAME_ATTR);
				stopName = ce.getAttribute(STOP_NAME_ATTR);
				startDescription = ce.getAttribute(START_DESCRIPTION_ATTR);
				stopDescription = ce.getAttribute(STOP_DESCRIPTION_ATTR);
				try {
					IServerRunner runner = (IServerRunner) ce
							.createExecutableExtension(CLASS_ATTR);
					runnerTypes.put(id,
							new ServerRunnerType(id, startName, stopName,
									startDescription, stopDescription, runner));
				} catch (CoreException e) {
					Trace.trace(Trace.STRING_SEVERE,
							"Error while loading server runtimes manager.", e);
				}
			}
		}

	}

	@Override
	protected String getPluginId() {
		return Activator.PLUGIN_ID;
	}

	@Override
	protected String getExtensionPoint() {
		return SHELL_RUNNER_MANAGER_EXTENSION_POINT;
	}

}
