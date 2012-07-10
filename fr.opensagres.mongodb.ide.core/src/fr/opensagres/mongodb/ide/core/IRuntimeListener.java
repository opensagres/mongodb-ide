package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public interface IRuntimeListener {

	void runtimeAdded(MongoRuntime runtime);
	
	void runtimeRemoved(MongoRuntime runtime);
	
}
