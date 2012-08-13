package com.mongodb.tools.driver.pagination;

import com.mongodb.CommandResult;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.gridfs.GridFS;

public class MongoHQTest {

	public static void main(String[] args) {
		try {

			
	
			
			MongoURI mongoURI = new MongoURI("mongodb://localhost:27017/test");

			Mongo mongo = new Mongo(mongoURI);

			GridFS gfsPhoto = new GridFS(mongo.getDB("gridfs-test"), "photo");
			DBCursor cursor = gfsPhoto.getFileList();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			
			CommandResult r = mongo.getDB("test").getStats();
			System.err.println(r);

			CommandResult collectionStats = mongo.getDB("test")
					.getCollection("foo").getStats();
			System.err.println(collectionStats);

			System.err.println(mongo.getDatabaseNames());

			mongo.getDB("testangelo").authenticate("a", "a".toCharArray());
			System.out.println(mongo.getDB("testangelo").getCollectionNames());
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static void main2(String[] args) {
		try {

			MongoURI mongoURI = new MongoURI(
					"mongodb://a:a@staff.mongohq.com:10093/testangelo");

			Mongo mongo = new Mongo(mongoURI);

			mongo.getDatabaseNames();

			mongo.getDB("testangelo").authenticate("a", "a".toCharArray());
			System.out.println(mongo.getDB("testangelo").getCollectionNames());
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}
