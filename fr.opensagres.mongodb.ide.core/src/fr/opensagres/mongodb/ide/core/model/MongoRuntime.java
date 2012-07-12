package fr.opensagres.mongodb.ide.core.model;

import java.io.File;

import org.eclipse.core.runtime.Path;

public class MongoRuntime {

	private final String id;
	private String name;
	private String installDir;
	private Path mongoProcessLocation;
	private Path mongodProcessLocation;
	private ProcessExtension processExtension;

	public MongoRuntime(String name, String path)
			throws InvalidInstallDirException {
		this(String.valueOf(System.currentTimeMillis()), name, path);
	}

	public MongoRuntime(String id, String name, String path)
			throws InvalidInstallDirException {
		this.id = id;
		setName(name);
		setInstallDir(path);
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstallDir() {
		return installDir;
	}

	public void setInstallDir(String installDir)
			throws InvalidInstallDirException {
		this.processExtension = validateInstallDir(installDir);
		this.installDir = installDir;
		this.mongoProcessLocation = new Path(new File(installDir, "/bin/mongo."
				+ processExtension).toString());
		this.mongodProcessLocation = new Path(new File(installDir,
				"/bin/mongod." + processExtension).toString());
	}

	public Path getMongoProcessLocation() {
		return mongoProcessLocation;
	}

	public Path getMongodProcessLocation() {
		return mongodProcessLocation;
	}

	public static ProcessExtension validateInstallDir(String installDir)
			throws InvalidInstallDirException {
		File installDirFile = new File(installDir);
		if (!installDirFile.exists()) {
			throw new InvalidInstallDirException();
		}
		if (!installDirFile.isDirectory()) {
			throw new InvalidInstallDirException();
		}
		File binFile = new File(installDirFile, "bin");
		if (!binFile.exists()) {
			throw new InvalidInstallDirException();
		}
		// Check if mongo.exe or mongo.sh exists in the bin folder
		File mongoFile = new File(binFile, "mongo.exe");
		if (!mongoFile.exists()) {
			mongoFile = new File(binFile, "mongo.sh");
			if (!mongoFile.exists()) {
				throw new InvalidInstallDirException();
			}
			return ProcessExtension.sh;
		}
		return ProcessExtension.exe;
	}

	public ProcessExtension getProcessExtension() {
		return processExtension;
	}
}
