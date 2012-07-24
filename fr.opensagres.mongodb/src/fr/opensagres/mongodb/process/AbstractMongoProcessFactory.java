package fr.opensagres.mongodb.process;

import java.io.File;
import java.io.IOException;

import fr.opensagres.mongodb.helper.ProcessHelper;

public abstract class AbstractMongoProcessFactory {

	private final File mongoBaseDir;
	private final File processFile;

	public AbstractMongoProcessFactory(File mongoBaseDir, String processName) {
		this.mongoBaseDir = mongoBaseDir;
		this.processFile = ProcessHelper.getProcessFile(mongoBaseDir,
				processName);
	}

	public File getMongoBaseDir() {
		return mongoBaseDir;
	}

	public File getProcessFile() {
		return processFile;
	}

	public String getVersion() throws IOException {
		return ProcessHelper.getVersion(getProcessFile());
	}

}
