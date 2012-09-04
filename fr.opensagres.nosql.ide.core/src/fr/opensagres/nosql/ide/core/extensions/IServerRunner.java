package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.IServer;

/**
 * Server Runner is used to implement the start/stop of the Mongo Server.
 * 
 */
public interface IServerRunner {

	/**
	 * Start the given server or connect to the given server.
	 * 
	 * @param server
	 * @throws Exception
	 */
	void start(IServer server) throws Exception;

	/**
	 * Stop the given server or disconnect to the given server.
	 * 
	 * @param server
	 * @param force
	 * @throws Exception
	 */
	void stop(IServer server, boolean force) throws Exception;

	/**
	 * Return true if the given server can support this server runner and false
	 * otherwise.
	 * 
	 * @param server
	 * @return
	 */
	boolean canSupport(IServer server);

}
