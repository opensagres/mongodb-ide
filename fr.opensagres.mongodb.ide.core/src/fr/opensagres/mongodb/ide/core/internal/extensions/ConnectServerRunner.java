package fr.opensagres.mongodb.ide.core.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;

public class ConnectServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(Server server) {
		return true;
	}

	public void start(Server server) throws Exception {
		server.setServerState(ServerState.Connecting);
		// Try to connect
		// MongoDriverHelper.tryConnection(getMongo());
		// Connection is OK, update the server state as connected.
		server.setServerState(ServerState.Connected);

	}

	public void stop(Server server, boolean force) throws Exception {
		server.disposeMongo();
		server.setServerState(ServerState.Disconnected);
	}

}
