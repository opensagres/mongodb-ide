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
package fr.opensagres.nosql.ide.ui.internal.extensions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.nosql.ide.core.extensions.AbstractRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.ui.extensions.IDialogFactory;
import fr.opensagres.nosql.ide.ui.extensions.IDialogFactoryRegistry;
import fr.opensagres.nosql.ide.ui.internal.Activator;
import fr.opensagres.nosql.ide.ui.internal.Trace;

/**
 * Registry which holds instance of {@link IDialogFactory} created by the
 * fr.opensagres.nosql.ide.core.dialogFactorys" extension point.
 * 
 */
public class DialogFactoryRegistry extends AbstractRegistry implements
		IDialogFactoryRegistry {

	private static final IDialogFactoryRegistry INSTANCE = new DialogFactoryRegistry();

	private static final String DIALOG_FACTORY_ELT = "dialogFactory";
	public static final String SERVER_TYPE_ATTR = "serverType";
	private static final String DIALOG_TYPE_ATTR = "dialogType";
	private static final String DIALOG_FACTORIES_EXTENSION_POINT = "dialogFactories";	

	public static IDialogFactoryRegistry getInstance() {
		return INSTANCE;
	}

	private Map<String, IDialogFactory> dialogFactories = new HashMap<String, IDialogFactory>();

	/**
	 * Return the {@link IDialogFactory} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IDialogFactory getFactory(IServerType serverType, String dialogType) {
		if (serverType == null || dialogType == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		String key = getKey(serverType, dialogType);
		return dialogFactories.get(key);
	}

	private String getKey(IServerType serverType, String dialogType) {
		return new StringBuilder(serverType.getId()).append("_").append(dialogType)
				.toString();
	}

	/**
	 * Return the list of the {@link IDialogFactory}.
	 * 
	 * @return
	 */
	public Collection<IDialogFactory> getFactories() {
		loadRegistryIfNedded();
		return dialogFactories.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseDialogFactoryManagers(cf);
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
			parseDialogFactoryManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each dialog element
	 * an instance of {@link IDialogFactory}.
	 * 
	 * <pre>
	 * 	  <extension
	 *       point="fr.opensagres.nosql.ide.ui.dialogFactoryies">
	 *    <dialogFactory
	 *          id="fr.opensagres.nosql.ide.mongodb.core"
	 *          name="%dialogFactory.name">
	 *    </dialogFactory>
	 * </extension>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseDialogFactoryManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			IServerType serverType = null;
			String serverTypeId = null;
			String dialogType = null;
			IDialogFactory factory = null;
			if (DIALOG_FACTORY_ELT.equals(ce.getName())) {
				serverTypeId = ce.getAttribute(SERVER_TYPE_ATTR);				
				serverType = fr.opensagres.nosql.ide.core.Platform
						.getServerTypeRegistry().getType(serverTypeId);
				dialogType = ce.getAttribute(DIALOG_TYPE_ATTR);
				try {
					factory = (IDialogFactory) ce
							.createExecutableExtension(CLASS_ATTR);
					String key = getKey(serverType, dialogType);
					dialogFactories.put(key, factory);
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
		return DIALOG_FACTORIES_EXTENSION_POINT;
	}

}
