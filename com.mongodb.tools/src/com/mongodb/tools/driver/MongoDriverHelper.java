package com.mongodb.tools.driver;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

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
			String username, char[] passwd) throws UnknownHostException,
			MongoException {
		Mongo mongo = MongoDriverFactory.createMongo(host, port);
		stopMongoServerAndCloseIt(mongo, username, passwd);
	}

	public static void stopMongoServerAndCloseIt(Mongo mongo, String username,
			char[] passwd) {
		try {
			stopMongoServer(mongo, username, passwd);
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}

	public static void stopMongoServer(Mongo mongo, String username,
			char[] passwd) {
		DB db = mongo.getDB("admin");
		if (username != null) {
			db.authenticate(username, passwd);
		}
		CommandResult shutdownResult = db.command(new BasicDBObject("shutdown",
				1));
		shutdownResult.throwOnError();
	}

	public static void tryConnection(Mongo mongo, String dbname)
			throws MongoException {
		if (dbname == null || dbname.length() < 1) {
			mongo.getDatabaseNames();
		} else {
			mongo.getDB(dbname).getCollectionNames();
		}
	}

	/**
	 * Seehttp://www.mongodb.org/display/DOCS/Connections
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param databaseName
	 * @return
	 */
	public static MongoURI createMongoURI(String host, Integer port,
			String userName, String password, String databaseName) {
		return new MongoURI(createStringMongoURI(host, port, userName,
				password, databaseName));
	}

	public static String createStringMongoURI(String host, Integer port,
			String userName, String password, String databaseName) {
		StringBuilder uri = new StringBuilder("mongodb://");

		if (userName != null && userName.length() > 0) {
			// username:password@
			uri.append(userName);
			uri.append(":");
			uri.append(password);
			uri.append("@");
		}
		uri.append(host);
		if (port != null) {
			uri.append(":");
			uri.append(String.valueOf(port));
		}
		if (databaseName != null && databaseName.length() > 0) {
			uri.append("/");
			uri.append(databaseName);
		}
		return uri.toString();
	}
}
