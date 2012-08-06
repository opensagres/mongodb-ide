package fr.opensagres.mongodb.ide.launching.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.launching.internal.ServerLauncherManager;

public class InternalServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(Server server) {
		return server.hasRuntime();
	}

	public void start(Server server) throws Exception {
		ServerLauncherManager.start(server);
	}

	public void stop(Server server, boolean force) throws Exception {
		ServerLauncherManager.stop(server, force);
	}

}
