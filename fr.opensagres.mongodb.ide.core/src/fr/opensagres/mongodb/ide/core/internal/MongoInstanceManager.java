package fr.opensagres.mongodb.ide.core.internal;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.MongoDriverFactory;

import fr.opensagres.mongodb.ide.core.IMongoInstanceManager;

public class MongoInstanceManager extends ArrayList<Mongo> implements
		IMongoInstanceManager {

	public MongoInstanceManager() {
	}

	public Mongo createMongo(String host, Integer port)
			throws UnknownHostException, MongoException {
		Mongo mongo = MongoDriverFactory.createMongo(host, port);
		super.add(mongo);
		return mongo;
	}

	public void dispose() {
		for (Mongo mongo : this) {
			try {
				dispose(mongo);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

	public void dispose(Mongo mongo) {
		if (super.contains(mongo)) {
			mongo.close();
			super.remove(mongo);
		}
	}
}
