package fr.opensagres.mongodb.ide.core.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.mongodb.ide.core.model.Server;

public class ExternalServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(Server server) {
		return server.hasRuntime();
	}

	public void start(Server server) throws Exception {
		// TODO : start cmd/sh shell.
	}

	public void stop(Server server, boolean force) throws Exception {
		// Do nothing
	}

}
