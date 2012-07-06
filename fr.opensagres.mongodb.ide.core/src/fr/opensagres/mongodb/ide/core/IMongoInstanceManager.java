package fr.opensagres.mongodb.ide.core;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public interface IMongoInstanceManager {

	Mongo createMongo(String host, Integer port) throws UnknownHostException,
			MongoException;

	void dispose();
	
	void dispose(Mongo mongo);
}
