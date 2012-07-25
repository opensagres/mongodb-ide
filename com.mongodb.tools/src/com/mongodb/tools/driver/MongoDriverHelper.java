package com.mongodb.tools.driver;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDriverHelper {

	public static void stopMongoServerWithoutError(String host, Integer port) {
		try {
			stopMongoServer(host, port, null, null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			// https://jira.mongodb.org/browse/JAVA-577
			e.printStackTrace();
		}
	}

	public static void stopMongoServer(String host, Integer port)
			throws UnknownHostException, MongoException {
		stopMongoServer(host, port, null, null);
	}

	public static void stopMongoServer(String host, Integer port,
			String username, String passwd) throws UnknownHostException,
			MongoException {
		Mongo mongo = MongoDriverFactory.createMongo(host, port);
		stopMongoServerAndCloseIt(mongo, username, passwd);
	}

	public static void stopMongoServerAndCloseIt(Mongo mongo, String username,
			String passwd) {
		try {
			stopMongoServer(mongo, username, passwd);
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}

	public static void stopMongoServer(Mongo mongo, String username,
			String passwd) {
		DB db = mongo.getDB("admin");
		if (username != null) {
			db.authenticate(username, passwd.toCharArray());
		}
		CommandResult shutdownResult = db.command(new BasicDBObject("shutdown",
				1));
		shutdownResult.throwOnError();
	}

	public static void tryConnection(Mongo mongo) throws MongoException {
		mongo.getDatabaseNames();
	}

}
