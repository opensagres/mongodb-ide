package com.mongodb.tools.driver;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DBObjectHelper {

	public static DBCursor getSystemUsersAsCursor(DB db) {
		DBCollection collection = db.getCollection("system.users");
		return collection.find();
	}

	public static List<DBObject> getSystemUsers(DB db) {
		List<DBObject> users = new ArrayList<DBObject>();
		DBCursor cursor = getSystemUsersAsCursor(db);
		while (cursor.hasNext()) {
			DBObject object = cursor.next();
			users.add(object);
		}
		return users;
	}

	public static String getUsernameOfUser(DBObject user) {
		return ((BasicDBObject) user).getString("user");
	}

	public static String getId(DBObject object) {
		return ((BasicDBObject) object).getString("_id");
	}

	public static boolean isReadonlyOfUser(DBObject user) {
		String readOnly = ((BasicDBObject) user).getString("readOnly");
		try {
			return Boolean.parseBoolean(readOnly);
		} catch (Throwable e) {
			return true;
		}
	}
}
