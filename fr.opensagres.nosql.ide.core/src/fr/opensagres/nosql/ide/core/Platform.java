package fr.opensagres.nosql.ide.core;

import fr.opensagres.nosql.ide.core.extensions.IRuntimeFactoryRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerFactoryRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerRunnerRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerTypeRegistry;
import fr.opensagres.nosql.ide.core.internal.ServerManager;
import fr.opensagres.nosql.ide.core.internal.ServerRuntimeManager;
import fr.opensagres.nosql.ide.core.internal.extensions.RuntimeFactoryRegistry;
import fr.opensagres.nosql.ide.core.internal.extensions.ServerFactoryRegistry;
import fr.opensagres.nosql.ide.core.internal.extensions.ServerRunnerRegistry;
import fr.opensagres.nosql.ide.core.internal.extensions.ServerTypeRegistry;
import fr.opensagres.nosql.ide.core.internal.shell.ShellCommandManagerRegistry;
import fr.opensagres.nosql.ide.core.shell.IShellCommandManagerRegistry;

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

	public static IServerRunnerRegistry getServerRunnerRegistry() {
		return ServerRunnerRegistry.getInstance();
	}

	public static IShellCommandManagerRegistry getShellCommandManagerRegistry() {
		return ShellCommandManagerRegistry.getInstance();
	}

	public static IRuntimeFactoryRegistry getRuntimeFactoryRegistry() {
		return RuntimeFactoryRegistry.getInstance();
	}

}
