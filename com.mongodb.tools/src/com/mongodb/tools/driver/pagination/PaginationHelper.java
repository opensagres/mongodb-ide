package com.mongodb.tools.driver.pagination;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PaginationHelper {

	public static Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage) {
		return paginate(collection.find(), pageNumber, itemsPerPage);
	}

	public static Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		return paginate(collection.find(), pageNumber, itemsPerPage, sortName,
				order);
	}

	public static Page paginate(DBCursor dbCursor, int pageNumber,
			int itemsPerPage) {
		return paginate(dbCursor, pageNumber, itemsPerPage, null, null);
	}

	public static Page paginate(DBCursor dbCursor, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		DBCursor cursor = dbCursor;
		if (sortName != null && order != null) {
			DBObject orderBy = new BasicDBObject(sortName,
					(SortOrder.DESCENDING == order) ? -1 : 1);
			cursor.sort(orderBy);
		}
		cursor = cursor.skip((pageNumber) * itemsPerPage).limit(itemsPerPage);
		List<DBObject> content = cursor.toArray();
		int totalItems = dbCursor.count();
		return new Page(content, totalItems);
	}
}
