package com.mongodb.tools.driver.pagination;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.DBObjectHelper;

public class DBObjectHelperTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		Mongo mongo = new Mongo("localhost");
		mongo.getDB("test").addUser("a", "a".toCharArray());
		List<DBObject> users = DBObjectHelper.getSystemUsers(mongo.getDB("test"));

		System.err.println(users);
	}
}
