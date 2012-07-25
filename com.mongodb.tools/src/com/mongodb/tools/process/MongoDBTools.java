package com.mongodb.tools.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mongodb.tools.process.InvalidMongoHomeDirException.InvalidInstallDirType;

/**
 * MongoDB Tools.
 * 
 */
public class MongoDBTools {

	private static final String EXE_EXTENSION = ".exe";
	private static final String SH_EXTENSION = ".sh";

	/**
	 * Returns the process file (mongod.exe, mongo.exe, etc) of teh given mongo
	 * home dir and process name.
	 * 
	 * @param mongoBaseDir
	 * @param processName
	 * @return
	 */
	public static File getProcessFile(File mongoBaseDir,
			MongoProcessName processName) {
		File f = new File(mongoBaseDir, processName.name() + EXE_EXTENSION);
		if (f.exists()) {
			return f;
		}
		return new File(mongoBaseDir, processName.name() + SH_EXTENSION);
	}

	/**
	 * Validate the mongo home dir and returns an Holder which contaisn list of
	 * process files.
	 * 
	 * @param homeDir
	 * @return
	 * @throws InvalidMongoHomeDirException
	 */
	public static MongoProcessFiles validateMongoHomeDir(String homeDir)
			throws InvalidMongoHomeDirException {
		File installDirFile = new File(homeDir);
		if (!installDirFile.exists()) {
			throw new InvalidMongoHomeDirException(installDirFile,
					InvalidInstallDirType.baseDirNotExists);
		}
		if (!installDirFile.isDirectory()) {
			throw new InvalidMongoHomeDirException(installDirFile,
					InvalidInstallDirType.baseDirNotDir);
		}
		File binFile = new File(installDirFile, "bin");
		if (!binFile.exists()) {
			throw new InvalidMongoHomeDirException(binFile,
					InvalidInstallDirType.binDirNotExists);
		}
		// Check if mongo.exe or mongo.sh exists in the bin folder
		File mongodFile = getProcessFile(binFile, MongoProcessName.mongod);
		if (!mongodFile.exists()) {
			if (!mongodFile.exists()) {
				throw new InvalidMongoHomeDirException(mongodFile,
						InvalidInstallDirType.processFileNotExists);
			}
		}
		File mongoFile = getProcessFile(binFile, MongoProcessName.mongo);
		return new MongoProcessFiles(mongodFile, mongoFile);
	}

	/**
	 * Execute the process and returns the result in a String.
	 * 
	 * @param processBuilder
	 * @return
	 * @throws IOException
	 */
	public static String execute(ProcessBuilder processBuilder)
			throws IOException {
		Process pwd = processBuilder.start();
		BufferedReader outputReader = new BufferedReader(new InputStreamReader(
				pwd.getInputStream()));
		String output;
		StringBuilder lines = new StringBuilder();
		while ((output = outputReader.readLine()) != null) {
			lines.append(output.toString());
		}
		return lines.toString();
	}
}
