package com.mongodb.tools.process.mongo;

import java.io.File;
import java.io.IOException;

import com.mongodb.tools.process.MongoDBTools;

/**
 * Tools for executing command with the MONGODB_DIR/bin/mongo.exe.
 * 
 */
public class MongoTools {

	/**
	 * 
	 * Execute the given MONGODB_DIR/bin/mongo.exe process with -version to
	 * retrieve the version of the Shell
	 * 
	 * @param mongodFile
	 *            the MONGODB_DIR/bin/mongo.exe file.
	 * @return
	 * @throws IOException
	 */
	public static String getVersion(File mongoProcess) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(
				mongoProcess.getPath(), "-version");
		processBuilder.redirectErrorStream(true);
		return MongoDBTools.execute(processBuilder);
	}
}
