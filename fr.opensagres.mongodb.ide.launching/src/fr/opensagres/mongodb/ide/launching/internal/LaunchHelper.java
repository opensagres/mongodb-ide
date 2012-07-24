package fr.opensagres.mongodb.ide.launching.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;

public class LaunchHelper {

	public static final String ATTR_SERVER_ID = "MongoServer";
	public static final String ATTR_DATABASE_ID = "MongoDatabase";
	public static final String ATTR_RUNTIME_ID = "MongoRuntime";
	private static ILaunchConfigurationType mongodLaunchConfigurationType;
	private static ILaunchConfigurationType mongoLaunchConfigurationType;

	/**
	 * Returns the server associated with the given launch configuration.
	 * 
	 * @param configuration
	 *            a launch configuration
	 * @return the server associated with the launch configuration, or
	 *         <code>null</code> if no server could be found
	 * @throws CoreException
	 *             if there is a problem getting the attribute from the launch
	 *             configuration
	 */
	public static Server getServer(ILaunchConfiguration configuration)
			throws CoreException {
		String serverId = configuration.getAttribute(ATTR_SERVER_ID,
				(String) null);

		if (serverId != null)
			return Platform.getServerManager().findServer(serverId);
		return null;
	}

	public static Database getDatabase(ILaunchConfiguration configuration)
			throws CoreException {
		String databaseId = configuration.getAttribute(ATTR_DATABASE_ID,
				(String) null);

		if (databaseId != null) {
			String[] ids = Database.getIds(databaseId);
			String serverId = ids[0];
			Server server = Platform.getServerManager().findServer(serverId);
			if (server != null) {
				String databaseName = ids[1];
				return server.findDatabase(databaseName);
			}
		}
		return null;
	}

	public static ILaunchConfigurationType getMongodLaunchConfigurationType() {
		if (mongodLaunchConfigurationType != null) {
			return mongodLaunchConfigurationType;
		}
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		mongodLaunchConfigurationType = launchManager
				.getLaunchConfigurationType("fr.opensagres.mongodb.ide.launching.mongod.launchConfigurationType");
		return mongodLaunchConfigurationType;
	}

	public static ILaunchConfigurationType getMongoLaunchConfigurationType() {
		if (mongoLaunchConfigurationType != null) {
			return mongoLaunchConfigurationType;
		}
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		mongoLaunchConfigurationType = launchManager
				.getLaunchConfigurationType("fr.opensagres.mongodb.ide.launching.mongo.launchConfigurationType");
		return mongoLaunchConfigurationType;
	}

}
