package fr.opensagres.mongodb.ide.core.model;

import org.eclipse.core.runtime.Path;

import com.mongodb.tools.process.InvalidMongoHomeDirException;
import com.mongodb.tools.process.MongoDBTools;
import com.mongodb.tools.process.MongoProcessFiles;

/**
 * 
 * MongoRuntime stores an installation directory of a MongoDB dababase. It is
 * used :
 * 
 * <ul>
 * <li>start/stop a local MongoDB server from ServerExplorer View with launch by
 * calling INSTAL_DIR/bin/mongod.exe/sh process.</li> * *
 * <li>start/stop a MongoDB Shell from ServerExplorer View (by selecting a
 * database) with launch by calling INSTAL_DIR/bin/mongo.exe/sh process.</li>
 * </ul>
 * 
 */
public class MongoRuntime {

	private final String id;
	private String name;
	private String installDir;
	private Path mongoProcessLocation;
	private Path mongodProcessLocation;

	public MongoRuntime(String name, String path)
			throws InvalidMongoHomeDirException {
		this(String.valueOf(System.currentTimeMillis()), name, path);
	}

	public MongoRuntime(String id, String name, String path)
			throws InvalidMongoHomeDirException {
		this.id = id;
		setName(name);
		setInstallDir(path);
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
			throws InvalidMongoHomeDirException {
		MongoProcessFiles files = MongoDBTools.validateMongoHomeDir(installDir);
		this.installDir = installDir;
		this.mongoProcessLocation = new Path(files.getMongoFile().getPath());
		this.mongodProcessLocation = new Path(files.getMongodFile().getPath());
	}

	public Path getMongoProcessLocation() {
		return mongoProcessLocation;
	}

	public Path getMongodProcessLocation() {
		return mongodProcessLocation;
	}

}
