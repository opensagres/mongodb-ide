package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.utils.CommandExecHelper;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;

public class ExternalServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(IServer server) {
		return server.hasRuntime();
	}

	public void start(IServer server) throws Exception {
		// TODO : start cmd/sh shell.
		String cmd = ((MongoServer) server).getMongoServerCommand(true);
		CommandExecHelper.exec(cmd);
	}

	public void stop(IServer server, boolean force) throws Exception {
		// Do nothing
	}

}
