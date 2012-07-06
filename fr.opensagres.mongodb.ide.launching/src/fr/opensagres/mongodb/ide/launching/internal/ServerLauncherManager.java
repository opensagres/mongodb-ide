package fr.opensagres.mongodb.ide.launching.internal;

import fr.opensagres.mongodb.ide.core.IServerLauncherManager;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.MongoHelper;

public class ServerLauncherManager implements IServerLauncherManager {

	public void start(Server server) throws Exception {
		// TODO Auto-generated method stub

	}

	public void stop(Server server) throws Exception {
		MongoHelper.stopMongoServerAndCloseIt(server.getMongo(),
				server.getUserName(), server.getPassword());
	}

}
