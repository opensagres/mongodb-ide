package fr.opensagres.mongodb.ide.core;

import java.util.List;

import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerManager extends ISettingsManager{

	void addServer(Server server) throws Exception;

	void removeServer(Server server) throws Exception;

	List<Server> getServers();

	void addListener(IServerListener listener);

	void removeListener(IServerListener listener);

	void dispose();

	Server findServer(String serverId);

}
