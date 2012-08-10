package com.mongodb.tools.driver;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;

public class StatsHelper {

	private static final String SIZE = "size";
	private static final String COUNT = "count";
	private static final String STORAGE = "storageSize";
	private static final String AVG_OBJ = "avgObjSize";
	private static final String PADDING = "paddingFactor";
	private static final String INDEX_SIZES = "indexSizes";

	public static Integer getSize(CommandResult stats) {
		return getInteger(stats, SIZE);
	}

	public static Integer getCount(CommandResult stats) {
		return getInteger(stats, COUNT);
	}

	public static Integer getStorage(CommandResult stats) {
		return getInteger(stats, STORAGE);
	}

	public static Integer getAvgObj(CommandResult stats) {
		return getInteger(stats, AVG_OBJ);
	}

	public static Integer getPadding(CommandResult stats) {
		return getInteger(stats, PADDING);
	}

	public static BasicDBObject getIndexSizes(CommandResult stats) {
		return (BasicDBObject) stats.get(INDEX_SIZES);
	}

	public static String formatAsBytes(Integer value) {
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}

	public static Integer getInteger(CommandResult stats, String key) {
		if (stats.containsField(key)) {
			return stats.getInt(key);
		}
		return null;
	}
}
