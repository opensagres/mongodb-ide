package fr.opensagres.nosql.ide.ui;

import fr.opensagres.nosql.ide.ui.extensions.IServerImageRegistry;
import fr.opensagres.nosql.ide.ui.internal.extensions.ServerImageRegistry;

public class PlatformUI {

	private final static IServerImageRegistry SERVER_IMAGE_REGISTRY_INSTANCE = new ServerImageRegistry();

	public static IServerImageRegistry getServerImageRegistry() {
		return SERVER_IMAGE_REGISTRY_INSTANCE;
	}
}
