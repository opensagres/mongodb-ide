package fr.opensagres.nosql.ide.core;

import fr.opensagres.nosql.ide.core.model.IServer;

public interface IServerLifecycleListener {

	/**
	 * A new server has been created.
	 * 
	 * @param server
	 *            the new server
	 */
	public void serverAdded(IServer server);

	/**
	 * An existing server has been updated or modified.
	 * 
	 * @param server
	 *            the modified server
	 */
	public void serverChanged(IServer server);

	/**
	 * A existing server has been removed.
	 * 
	 * @param server
	 *            the removed server
	 */
	public void serverRemoved(IServer server);

}
