package com.mongodb.tools.driver.pagination;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.pagination.Page;
import com.mongodb.tools.driver.pagination.PaginationHelper;

public class PaginationHelperTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost");
			DB db = mongo.getDB("test");
			DBCollection collection = db.getCollection("foo");

			DBCursor cursor = collection.find();
			while (cursor.hasNext()) {
				DBObject dbObject = (DBObject) cursor.next();
			//	System.err.println(dbObject);
			}

			DBCursor cursor1 = collection.find();
			Page<DBObject> result = PaginationHelper.paginate(cursor1, 2, 10);
			for (DBObject dbObject : cursor1) {
				System.err.println(dbObject);
			}
			System.err.println(result.getTotalElements());
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}
}
