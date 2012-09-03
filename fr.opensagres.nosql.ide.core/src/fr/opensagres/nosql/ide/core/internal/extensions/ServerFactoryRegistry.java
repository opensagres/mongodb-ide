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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.nosql.ide.core.extensions.AbstractRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerFactory;
import fr.opensagres.nosql.ide.core.extensions.IServerFactoryRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.internal.Activator;
import fr.opensagres.nosql.ide.core.internal.Trace;

/**
 * Registry which holds instance of {@link IServerFactory} created by the
 * fr.opensagres.nosql.ide.core.serverFactorys" extension point.
 * 
 */
public class ServerFactoryRegistry extends AbstractRegistry implements
		IServerFactoryRegistry {

	private static final IServerFactoryRegistry INSTANCE = new ServerFactoryRegistry();

	private static final String SERVER_FACTORY_ELT = "serverFactory";
	public static final String SERVER_TYPE_ATTR = "serverType";
	private static final String SERVER_FACTORIES_EXTENSION_POINT = "serverFactories";

	public static IServerFactoryRegistry getInstance() {
		return INSTANCE;
	}

	private Map<IServerType, IServerFactory> serverFactories = new HashMap<IServerType, IServerFactory>();

	/**
	 * Return the {@link IServerFactory} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IServerFactory getFactory(IServerType serverType) {
		if (serverType == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return serverFactories.get(serverType);
	}

	/**
	 * Return the list of the {@link IServerFactory}.
	 * 
	 * @return
	 */
	public Collection<IServerFactory> getFactories() {
		loadRegistryIfNedded();
		return serverFactories.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseServerFactoryManagers(cf);
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
			parseServerFactoryManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each server element
	 * an instance of {@link IServerFactory}.
	 * 
	 * <pre>
	 * 	  <extension
	 *       point="fr.opensagres.nosql.ide.core.serverFactorys">
	 *    <serverFactory
	 *          id="fr.opensagres.nosql.ide.mongodb.core"
	 *          name="%serverFactory.name">
	 *    </serverFactory>
	 * </extension>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseServerFactoryManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			IServerType serverType = null;
			String serverTypeId = null;
			IServerFactory factory = null;
			if (SERVER_FACTORY_ELT.equals(ce.getName())) {
				serverTypeId = ce.getAttribute(SERVER_TYPE_ATTR);
				serverType = fr.opensagres.nosql.ide.core.Platform
						.getServerTypeRegistry().getType(serverTypeId);
				try {
					factory = (IServerFactory) ce
							.createExecutableExtension(CLASS_ATTR);
					serverFactories.put(serverType, factory);
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
		return SERVER_FACTORIES_EXTENSION_POINT;
	}

}
