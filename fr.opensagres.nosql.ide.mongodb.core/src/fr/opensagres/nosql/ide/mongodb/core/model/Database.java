package fr.opensagres.nosql.ide.mongodb.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoException;

import fr.opensagres.nosql.ide.core.model.AbstractDatabase;
import fr.opensagres.nosql.ide.core.model.Users;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

public class Database extends AbstractDatabase {

	private DB db;
	private boolean alreadyAuthenticated;
	private CollectionsCategory collectionsCategory;

	public Database(String name) {
		super(name);
		this.alreadyAuthenticated = false;
		this.collectionsCategory = new CollectionsCategory();
		// force compute of static children nodes.
		getChildren();
	}

	@Override
	protected void doGetChildren() throws Exception {
		// Collections folder
		super.addNode(collectionsCategory);
		// Stored javascript folder
		super.addNode(new StoredJavascriptCategory());
		// // GridFS folder
		super.addNode(new GridFSCategory());
		// // Users
		super.addNode(new Users());
	}

	public DB getDB() throws UnknownHostException, MongoException {
		boolean databaseChanged = ((MongoServer) getParent())
				.selectDatabase(this);
		if (db == null || databaseChanged) {
			db = getInternalDB();
		}
		return db;
	}

	private DB getInternalDB() throws UnknownHostException, MongoException {
		MongoServer server = (MongoServer) getParent();
		// 1) use databseName
		DB db = MongoShellCommandManager.getInstance().use(server,
				server.getMongo(), getName());
		String username = server.getUsername();
		// 2) authenticate if needed
		if (StringUtils.isNotEmpty(username) && !alreadyAuthenticated) {
			MongoShellCommandManager.getInstance().authenticate(server, db,
					username, server.getPassword());
			alreadyAuthenticated = true;
		}
		return db;
	}

}
