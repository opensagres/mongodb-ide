package fr.opensagres.mongodb.ide.core;

import com.mongodb.tools.driver.MongoInstanceManager;

import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerRegistry;
import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerRegistry;
import fr.opensagres.mongodb.ide.core.internal.MongoRuntimeManager;
import fr.opensagres.mongodb.ide.core.internal.ServerManager;
import fr.opensagres.mongodb.ide.core.internal.extensions.ServerRunnerRegistry;
import fr.opensagres.mongodb.ide.core.internal.extensions.ShellRunnerRegistry;

public class Platform {

	private final static IServerManager SERVER_MANAGER_INSTANCE = new ServerManager();
	private static IMongoRuntimeManager MONGO_RUNTIME_MANAGER = new MongoRuntimeManager();
	private static final IServerRunnerRegistry SERVER_RUNNER_REGISTRY_INSTANCE = new ServerRunnerRegistry();
	private static final IShellRunnerRegistry SHELL_RUNNER_REGISTRY_INSTANCE = new ShellRunnerRegistry();

	public static IServerManager getServerManager() {
		return SERVER_MANAGER_INSTANCE;
	}

	public static MongoInstanceManager getMongoInstanceManager() {
		return MongoInstanceManager.getInstance();
	}

	public static IMongoRuntimeManager getMongoRuntimeManager() {
		return MONGO_RUNTIME_MANAGER;
	}

	public static IServerRunnerRegistry getServerRunnerRegistry() {
		return SERVER_RUNNER_REGISTRY_INSTANCE;
	}
	
	public static IShellRunnerRegistry getShellRunnerRegistry() {
		return SHELL_RUNNER_REGISTRY_INSTANCE;
	}

}
