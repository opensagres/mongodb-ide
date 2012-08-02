package com.mongodb.tools.driver;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoInstanceManager extends ArrayList<Mongo> {

	public static final MongoInstanceManager INSTANCE = new MongoInstanceManager();

	public static MongoInstanceManager getInstance() {
		return INSTANCE;
	}

	public MongoInstanceManager() {
	}

	public Mongo createMongo(String host, Integer port)
			throws UnknownHostException, MongoException {
		Mongo mongo = MongoDriverFactory.createMongo(host, port);
		super.add(mongo);
		return mongo;
	}

	public Mongo createMongo(MongoURI mongoURI) throws MongoException,
			UnknownHostException {
		Mongo mongo = MongoDriverFactory.createMongo(mongoURI);
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
