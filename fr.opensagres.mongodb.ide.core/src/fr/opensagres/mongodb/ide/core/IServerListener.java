package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Server;

public interface IServerListener {

	void serverAdded(Server server);
	
	void serverRemoved(Server server);
	
}
