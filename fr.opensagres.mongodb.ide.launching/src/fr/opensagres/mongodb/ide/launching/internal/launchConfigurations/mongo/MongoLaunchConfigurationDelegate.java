package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.launching.internal.LaunchHelper;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.MongoProcessType;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.ProcessLaunchConfigurationDelegate;

public class MongoLaunchConfigurationDelegate extends
		ProcessLaunchConfigurationDelegate {

	public MongoLaunchConfigurationDelegate() {
		super(MongoProcessType.mongo);
	}

	@Override
	protected String[] getArguments(ILaunchConfiguration configuration,
			MongoRuntime runtime) throws CoreException {
		List<String> args = new ArrayList<String>();

		Database database = LaunchHelper.getDatabase(configuration);
		Server server = database.getParent();
		Integer port = server.getPort();
		// see
		// http://www.mongodb.org/display/DOCS/Overview+-+The+MongoDB+Interactive+Shell

		// Host+Port
		StringBuilder hostPortAndDatabase = new StringBuilder();
		hostPortAndDatabase.append(server.getHost());
		if (port != null) {
			hostPortAndDatabase.append(":");
			hostPortAndDatabase.append(port.toString());
		}
		// Database
		hostPortAndDatabase.append("/");
		hostPortAndDatabase.append(database.getName());

		args.add(hostPortAndDatabase.toString());

		// Username+password
		String username = server.getUsername();
		if (StringUtils.isNotEmpty(username)) {
			args.add("-u");
			args.add(username);
		}
		char[] password = server.getPassword();
		if (password != null && password.length > 0) {
			args.add("-p");
			args.add(String.valueOf(password));
		}
		return args.toArray(StringUtils.EMPTY_STRING_ARRAY);
	}

	@Override
	protected IProcess createProcess(ILaunch launch, IPath location, Process p,
			Map processAttributes) {
		return DebugPlugin.newProcess(launch, p, location.toOSString(),
				processAttributes);
	}

	@Override
	protected MongoRuntime getRuntime(ILaunchConfiguration configuration)
			throws CoreException {
		Database database = LaunchHelper.getDatabase(configuration);
		if (database != null) {
			MongoRuntime runtime = database.getParent().getRuntime();
			if (runtime != null) {
				return runtime;
			}
		}
		return null;
	}
}
