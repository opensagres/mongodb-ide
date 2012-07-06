package fr.opensagres.mongodb.ide.core;

import java.util.List;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public interface IMongoRuntimeManager {

	List<MongoRuntime> getRuntimes();
	
}
