package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerLifecycleListener {

	/**
	 * A new server has been created.
	 * 
	 * @param server
	 *            the new server
	 */
	public void serverAdded(Server server);

	/**
	 * An existing server has been updated or modified.
	 * 
	 * @param server
	 *            the modified server
	 */
	public void serverChanged(Server server);

	/**
	 * A existing server has been removed.
	 * 
	 * @param server
	 *            the removed server
	 */
	public void serverRemoved(Server server);
	
}
