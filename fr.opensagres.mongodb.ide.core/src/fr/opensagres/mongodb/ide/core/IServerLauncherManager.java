package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerLauncherManager {

	void start(Server server) throws Exception;

	void stop(Server server, boolean force) throws Exception;
}
