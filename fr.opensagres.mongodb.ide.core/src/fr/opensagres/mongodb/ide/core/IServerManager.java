package fr.opensagres.mongodb.ide.core;

import java.util.List;

import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerManager {

	void addServer(Server server);

	void removeServer(Server server);

	List<Server> getServers();

	void addListener(IServerListener listener);

	void removeListener(IServerListener listener);

	void dispose();

}
