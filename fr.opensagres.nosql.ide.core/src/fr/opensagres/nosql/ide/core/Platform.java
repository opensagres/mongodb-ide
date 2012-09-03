package fr.opensagres.nosql.ide.core;

import fr.opensagres.nosql.ide.core.extensions.IServerFactoryRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerTypeRegistry;
import fr.opensagres.nosql.ide.core.internal.ServerManager;
import fr.opensagres.nosql.ide.core.internal.ServerRuntimeManager;
import fr.opensagres.nosql.ide.core.internal.extensions.ServerFactoryRegistry;
import fr.opensagres.nosql.ide.core.internal.extensions.ServerTypeRegistry;

public class Platform {

	public static IServerTypeRegistry getServerTypeRegistry() {
		return ServerTypeRegistry.getInstance();
	}

	public static IServerManager getServerManager() {
		return ServerManager.getInstance();
	}

	public static IServerFactoryRegistry getServerFactoryRegistry() {
		return ServerFactoryRegistry.getInstance();
	}

	public static IServerRuntimeManager getServerRuntimeManager() {
		return ServerRuntimeManager.getInstance();
	}
}
