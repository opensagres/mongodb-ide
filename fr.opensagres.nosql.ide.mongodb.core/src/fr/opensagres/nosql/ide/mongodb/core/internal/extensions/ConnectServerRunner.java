package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.AbstractServerRunner;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.NodeStatus;
import fr.opensagres.nosql.ide.core.model.ServerState;

public class ConnectServerRunner extends AbstractServerRunner {

	@Override
	protected boolean doCanSupport(IServer server) {
		return true;
	}

	public void start(IServer server) throws Exception {
		server.setServerState(ServerState.Connecting);
		//server.getChildren();
		//if (server.getStatus() == NodeStatus.StartedWithError) {
		//	server.setServerState(ServerState.Disconnected);
		//}
		//else {
			server.setServerState(ServerState.Connected);
		//}
//		// Try to connect
//		try {
//			MongoDriverHelper.tryConnection(((MongoServer) server).getMongo(),
//					server.getDatabaseName());
//			server.setServerState(ServerState.Connected);
//		} catch (Throwable e) {
//			server.getChildren().add(new Error(e));
//			server.setServerState(ServerState.Disconnected);
//		}
		// MongoDriverHelper.tryConnection(getMongo());
		// Connection is OK, update the server state as connected.

	}

	public void stop(IServer server, boolean force) throws Exception {
		server.setServerState(ServerState.Disconnected);
	}

}
