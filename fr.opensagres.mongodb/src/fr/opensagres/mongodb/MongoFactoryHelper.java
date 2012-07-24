package fr.opensagres.mongodb;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoFactoryHelper {

	public static Mongo createMongo(String host, Integer port)
			throws UnknownHostException, MongoException {
		if (port != null) {
			return new Mongo(host, port);
		} else {
			return new Mongo(host);
		}
	}
}
