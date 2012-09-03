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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.ui.extensions.AbstractUIRegistry;
import fr.opensagres.nosql.ide.ui.extensions.IServerImage;
import fr.opensagres.nosql.ide.ui.extensions.IServerImageRegistry;
import fr.opensagres.nosql.ide.ui.internal.Activator;

/**
 * Registry which holds instance of {@link IServerImage} created by the
 * fr.opensagres.nosql.ide.core.serverImages" extension point.
 * 
 */
public class ServerImageRegistry extends AbstractUIRegistry implements
		IServerImageRegistry {

	private static final String SERVER_IMAGE_ELT = "serverImage";
	public static final String SERVER_TYPE_ATTR = "serverType";

	private static final String SERVER_TYPES_EXTENSION_POINT = "serverImages";

	private Map<IServerType, IServerImage> serverImages = new HashMap<IServerType, IServerImage>();

	/**
	 * Return the {@link IServerImage} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	public IServerImage getServerImage(IServerType serverType) {
		if (serverType == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return serverImages.get(serverType);
	}

	public Image getImage(IServerType serverType) {
		IServerImage serverImage = getServerImage(serverType);
		if (serverImage == null) {
			return null;
		}
		return serverImage.getIcon();
	}

	/**
	 * Return the list of the {@link IServerImage}.
	 * 
	 * @return
	 */
	public Collection<IServerImage> getImages() {
		loadRegistryIfNedded();
		return serverImages.values();
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parseServerImageManagers(cf);
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
			parseServerImageManagers(cf);
		}
	}

	/**
	 * Parse elements of the extension poit and create for each server element
	 * an instance of {@link IServerImage}.
	 * 
	 * <pre>
	 * 	  <extension
	 *       point="fr.opensagres.nosql.ide.core.serverImages">
	 *    <serverImage
	 *          id="fr.opensagres.nosql.ide.mongodb.core"
	 *          name="%serverImage.name">
	 *    </serverImage>
	 * </extension>
	 * </pre>
	 * 
	 * @param cf
	 */
	private void parseServerImageManagers(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			String serverTypeId = null;
			Image icon = null;
			if (SERVER_IMAGE_ELT.equals(ce.getName())) {				
				serverTypeId = ce.getAttribute(SERVER_TYPE_ATTR);
				IServerType serverType = fr.opensagres.nosql.ide.core.Platform
						.getServerTypeRegistry().getType(serverTypeId);
				icon = getIconImage(ce);
				serverImages.put(serverType, new ServerImage(serverType, icon));
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
