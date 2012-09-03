package fr.opensagres.nosql.ide.core;

import java.util.List;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServer;

public interface IServerManager extends ISettingsManager {

	void addServer(IServer server) throws Exception;

	void removeServer(IServer server) throws Exception;

	List<IServer> getServers();

	List<IServer> getServers(IServerType serverType);

	void addServerLifecycleListener(IServerLifecycleListener listener);

	void removeServerLifecycleListener(IServerLifecycleListener listener);

	void dispose();

	IServer findServer(String serverId);

}
