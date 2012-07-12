package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongo;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
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
			MongoRuntime runtime) throws CoreException{

		return null;
	}

	@Override
	protected IProcess createProcess(ILaunch launch, IPath location, Process p,
			Map processAttributes) {
		return DebugPlugin.newProcess(launch, p, location.toOSString(),
				processAttributes);
	}

}
