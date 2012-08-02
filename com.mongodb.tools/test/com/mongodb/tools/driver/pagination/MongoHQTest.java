package com.mongodb.tools.driver.pagination;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;

public class MongoHQTest {

	public static void main(String[] args) {
		try {
			
			
			
			MongoURI mongoURI = new MongoURI(
					"mongodb://alocalhost:27017/test");
			
			
			Mongo mongo = new Mongo(mongoURI);
			
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
