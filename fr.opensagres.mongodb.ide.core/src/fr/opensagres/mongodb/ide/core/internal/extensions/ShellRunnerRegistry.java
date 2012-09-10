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
import fr.opensagres.mongodb.ide.core.extensions.IShellRunner;
import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerRegistry;
import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.internal.Activator;
import fr.opensagres.mongodb.ide.core.internal.Trace;

/**
 * Registry which holds instance of {@link IShellRunnerType} created by the
 * fr.opensagres.mongodb.ide.core.shellRunners" extension point.
 * 
 */
public class ShellRunnerRegistry extends AbstractRegistry implements
		IShellRunnerRegistry {

	private static final String SHELL_RUNNER_ELT = "shellRunner";
	public static final String START_NAME_ATTR = "startName";
	public static final String STOP_NAME_ATTR = "stopName";
	public static final String START_DESCRIPTION_ATTR = "startDescription";
	public static final String STOP_DESCRIPTION_ATTR = "stopDescription";

	private static final String SHELL_RUNNER_MANAGER_EXTENSION_POINT = "shellRunners";

	private Map<String, IShellRunnerType> runnerTypes = new HashMap<String, IShellRunnerType>();

	/**
	 * Return the {@link IShellRunnerType} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IShellRunnerType getRunnerType(String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return runnerTypes.get(id);
	}

	/**
	 * Return the list of the {@link IShellRunnerType}.
	 * 
	 * @return
	 */
	public Collection<IShellRunnerType> getRunners() {
		loadRegistryIfNedded();
		return runnerTypes.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseShellRunnerManagers(cf);
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
			parseShellRunnerManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each runner element
	 * an instance of {@link IShellRunnerType}.
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
	private void parseShellRunnerManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			String id = null;
			String startName = null;
			String stopName = null;
			String startDescription = null;
			String stopDescription = null;
			if (SHELL_RUNNER_ELT.equals(ce.getName())) {
				id = ce.getAttribute(ID_ATTR);
				startName = ce.getAttribute(START_NAME_ATTR);
				stopName = ce.getAttribute(STOP_NAME_ATTR);
				startDescription = ce.getAttribute(START_DESCRIPTION_ATTR);
				stopDescription = ce.getAttribute(STOP_DESCRIPTION_ATTR);
				try {
					IShellRunner runner = (IShellRunner) ce
							.createExecutableExtension(CLASS_ATTR);
					runnerTypes.put(id,
							new ShellRunnerType(id, startName, stopName,
									startDescription, stopDescription, runner));
				} catch (CoreException e) {
					Trace.trace(Trace.STRING_SEVERE,
							"Error while loading shell runtimes manager.", e);
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
