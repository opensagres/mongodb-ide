package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongo;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;

import fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.ProcessLaunchConfigurationDelegate;

public class MongoLaunchConfigurationDelegate extends
		ProcessLaunchConfigurationDelegate {

	public MongoLaunchConfigurationDelegate() {
		super("mongo");
	}

	@Override
	protected IProcess createProcess(ILaunch launch, IPath location, Process p,
			Map processAttributes) {
		return DebugPlugin.newProcess(launch, p, location.toOSString(),
				processAttributes);
	}

}
