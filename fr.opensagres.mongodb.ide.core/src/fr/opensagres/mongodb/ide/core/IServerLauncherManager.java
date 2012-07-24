package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerLauncherManager {

	void start(Server server) throws Exception;

	void stop(Server server, boolean force) throws Exception;

	void startShell(Database database);
	
	void stopShell(Database database);
}
