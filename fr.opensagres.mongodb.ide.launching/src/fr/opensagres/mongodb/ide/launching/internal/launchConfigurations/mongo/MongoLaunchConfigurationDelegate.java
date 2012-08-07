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

		// Host+Port+Database
		String hostPortAndDatabase = database.getStartMongoConsoleCommand();
		args.add(hostPortAndDatabase.toString());

		// Add other args like username, password, etc
		database.updateMongoConsoleArgs(args);

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
