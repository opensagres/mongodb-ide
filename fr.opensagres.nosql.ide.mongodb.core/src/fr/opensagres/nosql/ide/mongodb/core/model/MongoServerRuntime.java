package fr.opensagres.nosql.ide.mongodb.core.model;

import org.eclipse.core.runtime.Path;

import com.mongodb.tools.process.MongoDBTools;
import com.mongodb.tools.process.MongoProcessFiles;

import fr.opensagres.nosql.ide.core.model.AbstractServerRuntime;

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
public class MongoServerRuntime extends AbstractServerRuntime {

	private Path mongoProcessLocation;
	private Path mongodProcessLocation;

	public MongoServerRuntime(String name, String path) throws Exception {
		super(MongoServer.TYPE_ID, name, path);
	}

	public MongoServerRuntime(String id, String name, String path)
			throws Exception {
		super(MongoServer.TYPE_ID, id, name, path);
	}

	@Override
	protected void validateInstallDir(String installDir) throws Exception {
		MongoProcessFiles files = MongoDBTools.validateMongoHomeDir(installDir);
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
