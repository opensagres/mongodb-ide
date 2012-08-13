package com.mongodb.tools.driver.pagination;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;

public class PaginationHelper {

	/**
	 * Return a page result of document (DB Objects) of the given collection.
	 * 
	 * @param collection
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return
	 */
	public static Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage) {
		return paginate(collection.find(), pageNumber, itemsPerPage);
	}

	/**
	 * Return a page result of document (DB Objects) of the given collection
	 * with sort.
	 * 
	 * @param collection
	 * @param pageNumber
	 * @param itemsPerPage
	 * @param order
	 * @return
	 */
	public static Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		return paginate(collection.find(), pageNumber, itemsPerPage, sortName,
				order);
	}

	/**
	 * Return a page result of files (DB Objects) of the given gridFS.
	 * 
	 * @param gridFS
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return
	 */
	public static Page paginate(GridFS gridFS, int pageNumber, int itemsPerPage) {
		return paginate(gridFS.getFileList(), pageNumber, itemsPerPage);
	}

	/**
	 * Return a page result of files (DB Objects) of the given gridFS with sort.
	 * 
	 * @param gridFS
	 * @param pageNumber
	 * @param itemsPerPage
	 * @param order
	 * @return
	 */
	public static Page paginate(GridFS gridFS, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		return paginate(gridFS.getFileList(), pageNumber, itemsPerPage,
				sortName, order);
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
