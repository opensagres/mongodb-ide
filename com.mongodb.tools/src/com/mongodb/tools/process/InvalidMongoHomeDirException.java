package com.mongodb.tools.process;

import java.io.File;

/**
 * Exception thrown when Mongo Home dir is invalid..
 * 
 */
public class InvalidMongoHomeDirException extends Exception {

	public enum InvalidInstallDirType {
		baseDirNotExists, baseDirNotDir, binDirNotExists, processFileNotExists
	}

	private final File file;
	private final InvalidInstallDirType type;

	public InvalidMongoHomeDirException(File file, InvalidInstallDirType type) {
		this.file = file;
		this.type = type;
	}

	public File getFile() {
		return file;
	}

	public InvalidInstallDirType getType() {
		return type;
	}
}
