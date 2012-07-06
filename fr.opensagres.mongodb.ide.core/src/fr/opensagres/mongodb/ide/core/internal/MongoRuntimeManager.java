package fr.opensagres.mongodb.ide.core.internal;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.mongodb.ide.core.IMongoRuntimeManager;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public class MongoRuntimeManager extends ArrayList<MongoRuntime> implements
		IMongoRuntimeManager {

	public List<MongoRuntime> getRuntimes() {
		return this;
	}

}
