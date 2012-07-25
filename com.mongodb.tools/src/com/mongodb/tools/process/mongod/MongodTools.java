package com.mongodb.tools.process.mongod;

import java.io.File;
import java.io.IOException;

import com.mongodb.tools.process.MongoDBTools;

/**
 * Tools for executing command with the MONGODB_DIR/bin/mongod.exe.
 * 
 */
public class MongodTools {

	/**
	 * Execute the given MONGODB_DIR/bin/mongod.exe process with -version to
	 * retrieve the db version. Example :
	 * 
	 * <p>
	 * db version v2.0.2
	 * </p>
	 * 
	 * @param mongodFile
	 *            the MONGODB_DIR/bin/mongod.exe file.
	 * @return
	 * @throws IOException
	 */
	public static String getDBVersion(File mongodFile) throws IOException {
		// get db+pdf+git version
		// ex:db version v2.0.2, pdfile version 4.5Wed Jul 25 15:27:02 git
		// version: 514b122d308928517f5841888ceaa4246a7f18e3
		String version = getVersion(mongodFile);
		if (version != null) {
			// returns only db version v2.0.2
			int index = version.indexOf(",");
			if (index != -1) {
				return version.substring(0, index);
			}
		}
		return version;
	}

	/**
	 * 
	 * Execute the given MONGODB_DIR/bin/mongod.exe process with -version to
	 * retrieve the version (db+pdf+git). Example :
	 * 
	 * <p>
	 * db version v2.0.2, pdfile version 4.5Wed Jul 25 15:27:02 gitversion:
	 * 514b122d308928517f5841888ceaa4246a7f18e3
	 * </p>
	 * 
	 * @param mongodFile
	 *            the MONGODB_DIR/bin/mongod.exe file.
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
