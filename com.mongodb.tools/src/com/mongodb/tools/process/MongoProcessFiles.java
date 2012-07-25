package com.mongodb.tools.process;

import java.io.File;

/**
 * Holder to stores the MongoDB files stored in the MONGODB_DIR/bin (mongod.exe,
 * mongo.exe).
 * 
 */
public class MongoProcessFiles {

	private final File mongodFile;
	private final File mongoFile;

	public MongoProcessFiles(File mongodFile, File mongoFile) {
		this.mongodFile = mongodFile;
		this.mongoFile = mongoFile;
	}

	/**
	 * Returns the MONGODB_DIR/bin/mongod.exe (or mongod.sh) file process.
	 * 
	 * @return
	 */
	public File getMongodFile() {
		return mongodFile;
	}

	/**
	 * Returns the MONGODB_DIR/bin/mongo.exe (or mongo.sh) file process.
	 * 
	 * @return
	 */
	public File getMongoFile() {
		return mongoFile;
	}

}
