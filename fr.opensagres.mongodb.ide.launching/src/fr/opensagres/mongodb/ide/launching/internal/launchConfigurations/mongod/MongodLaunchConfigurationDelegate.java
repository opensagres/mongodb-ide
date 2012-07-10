package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.launching.internal.LaunchHelper;
import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.ProcessLaunchConfigurationDelegate;

public class MongodLaunchConfigurationDelegate extends
		ProcessLaunchConfigurationDelegate {

	public MongodLaunchConfigurationDelegate() {
		super("mongod");
	}

	@Override
	protected IProcess createProcess(ILaunch launch, IPath location, Process p,
			Map processAttributes) {
		return new MongodProcess(launch, p, location.toOSString(),
				processAttributes);
	}

	@Override
	protected MongoRuntime getRuntime(ILaunchConfiguration configuration)
			throws CoreException {
		Server server = LaunchHelper.getServer(configuration);
		if (server != null) {
			MongoRuntime runtime = server.getRuntime();
			if (runtime != null) {
				return runtime;
			}
		}
		return super.getRuntime(configuration);
	}

}
