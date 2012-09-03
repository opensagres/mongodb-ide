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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.nosql.ide.core.extensions.AbstractRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.extensions.IServerTypeRegistry;
import fr.opensagres.nosql.ide.core.internal.Activator;

/**
 * Registry which holds instance of {@link IServerType} created by the
 * fr.opensagres.nosql.ide.core.serverTypes" extension point.
 * 
 */
public class ServerTypeRegistry extends AbstractRegistry implements
		IServerTypeRegistry {

	private static final IServerTypeRegistry INSTANCE = new ServerTypeRegistry();
	private static final String SERVER_TYPE_ELT = "serverType";
	public static final String NAME_ATTR = "name";
	private static final String SERVER_TYPES_EXTENSION_POINT = "serverTypes";

	public static IServerTypeRegistry getInstance() {
		return INSTANCE;
	}

	private Map<String, IServerType> serverTypes = new HashMap<String, IServerType>();

	/**
	 * Return the {@link IServerType} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IServerType getType(String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return serverTypes.get(id);
	}

	/**
	 * Return the list of the {@link IServerType}.
	 * 
	 * @return
	 */
	public Collection<IServerType> getTypes() {
		loadRegistryIfNedded();
		return serverTypes.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseServerTypeManagers(cf);
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
			parseServerTypeManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each server element
	 * an instance of {@link IServerType}.
	 * 
	 * <pre>
	 * 	  <extension
	 *       point="fr.opensagres.nosql.ide.core.serverTypes">
	 *    <serverType
	 *          id="fr.opensagres.nosql.ide.mongodb.core"
	 *          name="%serverType.name">
	 *    </serverType>
	 * </extension>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseServerTypeManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			String id = null;
			String name = null;
			if (SERVER_TYPE_ELT.equals(ce.getName())) {
				id = ce.getAttribute(ID_ATTR);
				name = ce.getAttribute(NAME_ATTR);
				serverTypes.put(id, new ServerType(id, name));
			}
		}

	}

	@Override
	protected String getPluginId() {
		return Activator.PLUGIN_ID;
	}

	@Override
	protected String getExtensionPoint() {
		return SERVER_TYPES_EXTENSION_POINT;
	}

}
