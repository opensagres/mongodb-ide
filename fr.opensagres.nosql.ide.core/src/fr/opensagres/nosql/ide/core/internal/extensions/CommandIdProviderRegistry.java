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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.nosql.ide.core.extensions.AbstractRegistry;
import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.extensions.ICommandIdProviderRegistry;
import fr.opensagres.nosql.ide.core.extensions.IRuntimeFactory;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.internal.Activator;
import fr.opensagres.nosql.ide.core.internal.Trace;

/**
 * Registry which holds commandId String created by the
 * fr.opensagres.nosql.ide.core.commandIdProviders" extension point.
 * 
 */
public class CommandIdProviderRegistry extends AbstractRegistry implements
		ICommandIdProviderRegistry {

	private static final ICommandIdProviderRegistry INSTANCE = new CommandIdProviderRegistry();

	private static final String COMMAND_ID_PROVIDER_ELT = "commandIdProvider";
	private static final String COMMAND_ID_ATTR = "commandId";
	private static final String COMMAND_TYPE_ATTR = "commandType";
	public static final String SERVER_TYPE_ATTR = "serverType";
	private static final String COMMAND_ID_PROVIDERS_EXTENSION_POINT = "commandIdProviders";

	public static ICommandIdProviderRegistry getInstance() {
		return INSTANCE;
	}

	private Map<IServerType, ICommandIdProvider> commandIdProviders = new HashMap<IServerType, ICommandIdProvider>();

	public String getCommandId(IServerType serverType, int type, Object element) {
		if (serverType == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		ICommandIdProvider provider = commandIdProviders.get(serverType);
		return provider != null ? provider.getCommmandId(type, element) : null;
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseCommandIdProviderManagers(cf);
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
			parseCommandIdProviderManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each server element
	 * an instance of {@link ICommandIdProvider}.
	 * 
	 * <pre>
	 * 	     	<extension
	 * 	      point="fr.opensagres.nosql.ide.core.commandIdProviders">
	 * 	    <!-- Open Mongo Server Editor -->
	 * 	    <commandIdProvider
	 * 	          class="fr.opensagres.nosql.ide.mongodb.ui.internal.extension.MongoCommandIdProvider"
	 * 	          serverType="fr.opensagres.nosql.ide.mongodb.core">
	 * 	    </commandIdProvider>
	 * 	</extension>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseCommandIdProviderManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			IServerType serverType = null;
			String serverTypeId = null;
			ICommandIdProvider provider = null;
			if (COMMAND_ID_PROVIDER_ELT.equals(ce.getName())) {
				serverTypeId = ce.getAttribute(SERVER_TYPE_ATTR);
				serverType = fr.opensagres.nosql.ide.core.Platform
						.getServerTypeRegistry().getType(serverTypeId);
				try {
					provider = (ICommandIdProvider) ce
							.createExecutableExtension(CLASS_ATTR);
					commandIdProviders.put(serverType, provider);
				} catch (CoreException e) {
					Trace.trace(Trace.STRING_SEVERE, e.getMessage());
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
		return COMMAND_ID_PROVIDERS_EXTENSION_POINT;
	}

}
