package com.mongodb.tools.driver;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoDriverFactory {

	public static Mongo createMongo(String host, Integer port)
			throws UnknownHostException, MongoException {
		if (port != null) {
			return new Mongo(host, port);
		} else {
			return new Mongo(host);
		}
	}

	public static Mongo createMongo(MongoURI mongoURI) throws MongoException,
			UnknownHostException {
		return new Mongo(mongoURI);
	}
}
