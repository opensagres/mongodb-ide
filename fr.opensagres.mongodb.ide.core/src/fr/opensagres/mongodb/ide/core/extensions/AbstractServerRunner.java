package fr.opensagres.mongodb.ide.core.extensions;

import fr.opensagres.mongodb.ide.core.model.Server;

public abstract class AbstractServerRunner implements IServerRunner {

	public boolean canSupport(Server server) {
		if (server == null) {
			return false;
		}
		return doCanSupport(server);
	}

	protected abstract boolean doCanSupport(Server server);
	
}
