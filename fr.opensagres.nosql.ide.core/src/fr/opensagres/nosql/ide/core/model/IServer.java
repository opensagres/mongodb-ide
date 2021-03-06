package fr.opensagres.nosql.ide.core.model;

import java.io.IOException;
import java.io.Writer;

import fr.opensagres.nosql.ide.core.IServerListener;
import fr.opensagres.nosql.ide.core.extensions.IServerType;

public interface IServer extends ITreeContainerNode<IServer> {

	IServerType getServerType();

	void save(Writer writer) throws IOException;

	void dispose();

	void addServerListener(IServerListener serverListener);

	void removeServerListener(IServerListener serverListener);

	boolean isConnected();

	void setRuntime(IServerRuntime runtime);

	IServerRuntime getRuntime();

	ServerState getServerState();

	void setServerState(ServerState serverState);

	String getURL();

	String getDatabaseName();

	String getUsername();

	String getPassword();

	Integer getPort();

	String getHost();

	IDatabase createDatabase(String databaseName) throws Exception;

	void dropDatabase(IDatabase database) throws Exception;

	void deleteCollection(ICollection collection) throws Exception;

	boolean hasRuntime();
	
}
