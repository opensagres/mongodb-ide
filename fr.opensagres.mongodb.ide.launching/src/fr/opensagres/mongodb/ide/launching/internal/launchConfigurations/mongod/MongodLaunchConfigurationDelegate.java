package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;

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

}
