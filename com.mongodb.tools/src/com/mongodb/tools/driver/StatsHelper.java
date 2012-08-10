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

	public static double getSize(CommandResult stats) {
		return stats.getDouble(SIZE);
	}

	public static double getCount(CommandResult stats) {
		return stats.getDouble(COUNT);
	}

	public static double getStorage(CommandResult stats) {
		return stats.getDouble(STORAGE);
	}

	public static double getAvgObj(CommandResult stats) {
		return stats.getDouble(AVG_OBJ);
	}

	public static double getPadding(CommandResult stats) {
		return stats.getDouble(PADDING);
	}

	public static BasicDBObject getIndexSizes(CommandResult stats) {
		return (BasicDBObject) stats.get(INDEX_SIZES);
	}

	public static String formatAsBytes(double value) {
		return String.valueOf(value);
	}

}
