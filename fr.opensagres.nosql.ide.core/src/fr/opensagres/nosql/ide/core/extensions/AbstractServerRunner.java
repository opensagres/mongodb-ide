package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.IServer;

public abstract class AbstractServerRunner implements IServerRunner {

	public boolean canSupport(IServer server) {
		if (server == null) {
			return false;
		}
		return doCanSupport(server);
	}

	protected abstract boolean doCanSupport(IServer server);

}
