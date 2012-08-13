package com.mongodb.tools.driver;

import java.util.HashSet;
import java.util.Set;

import com.mongodb.DB;

public class GridFSHelper {

	/**
	 * bucket to use for the collection namespaces
	 */
	public static final String DEFAULT_BUCKET = "fs";

	private static final String FILES_EXT = ".files";

	public static Set<String> getGridFSBucketNames(DB db,
			boolean addDefaultBucketIfEmpty) {
		Set<String> bucketNames = new HashSet<String>();
		Set<String> collectionNames = db.getCollectionNames();
		for (String collectionName : collectionNames) {
			if (collectionName.endsWith(FILES_EXT)) {
				bucketNames.add(collectionName.substring(0,
						collectionName.length() - FILES_EXT.length()));
			}
		}
		if (addDefaultBucketIfEmpty && bucketNames.size() < 1) {
			bucketNames.add(DEFAULT_BUCKET);
		}
		return bucketNames;
	}
}
