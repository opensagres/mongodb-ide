package fr.opensagres.mongodb.ide.launching.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;

public class LaunchHelper {

	public static final String ATTR_SERVER_ID = "MongoServer";
	public static final String ATTR_RUNTIME_ID = "MongoRuntime";

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

	public static MongoRuntime getRuntime(ILaunchConfiguration configuration)
			throws CoreException {
		String runtimeId = configuration.getAttribute(ATTR_RUNTIME_ID,
				(String) null);

		if (runtimeId != null)
			return Platform.getMongoRuntimeManager().findRuntime(runtimeId);
		return null;
	}
}
