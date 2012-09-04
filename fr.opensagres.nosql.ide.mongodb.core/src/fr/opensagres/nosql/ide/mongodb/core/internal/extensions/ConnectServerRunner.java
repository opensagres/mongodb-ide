package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.ServerState;

public class ConnectServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(IServer server) {
		return true;
	}

	public void start(IServer server) throws Exception {
		server.setServerState(ServerState.Connecting);
		// Try to connect
		// MongoDriverHelper.tryConnection(getMongo());
		// Connection is OK, update the server state as connected.
		server.setServerState(ServerState.Connected);

	}

	public void stop(IServer server, boolean force) throws Exception {
		server.setServerState(ServerState.Disconnected);
	}

}
