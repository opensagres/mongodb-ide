package fr.opensagres.nosql.ide.mongodb.core.model;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import fr.opensagres.nosql.ide.core.model.AbstractDatabase;
import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.Users;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionListStats;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;
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
					username, server.getPasswordAsCharArray());
			alreadyAuthenticated = true;
		}
		return db;
	}

	public List<ICollection> getCollections() {
		return collectionsCategory.getCollections();
	}

	public CollectionsCategory getCollectionsCategory() {
		return collectionsCategory;
	}

	public List<CollectionStats> createStats() throws UnknownHostException,
			MongoException {
		List<ICollection> collections = getCollections();
		CollectionListStats collectionStats = new CollectionListStats(
				collections.size());
		for (ICollection collection : collections) {
			collectionStats.addCollection((Collection) collection);
		}
		return collectionStats;
	}

	public List<DBObject> getUsers() throws UnknownHostException,
			MongoException {
		return MongoShellCommandManager.getInstance().getSystemUsers(
				getParent(), getDB());
	}

	public String getMongoConsoleCommand(boolean withBaseDir) {

		StringBuilder connection = new StringBuilder("");
		if (!withBaseDir) {
			connection.append("mongo");
		} else {
			// mongo.exe
			MongoServerRuntime runtime = (MongoServerRuntime) getParent()
					.getRuntime();
			connection.append(runtime.getMongoProcessLocation().toFile()
					.toString());
		}

		// Host+Port/Database
		connection.append(" ");
		connection.append(getStartMongoConsoleCommand());

		// Args
		List<String> args = getMongoConsoleArgs();
		for (String arg : args) {
			connection.append(" ");
			connection.append(arg);
		}
		return connection.toString();
	}

	public List<String> getMongoConsoleArgs() {
		List<String> args = new ArrayList<String>();
		updateMongoConsoleArgs(args);
		return args;
	}

	public void updateMongoConsoleArgs(List<String> args) {
		// Username+password
		IServer server = getParent();
		String username = server.getUsername();
		if (StringUtils.isNotEmpty(username)) {
			args.add("-u");
			args.add(username);
		}
		String password = server.getPassword();
		if (StringUtils.isNotEmpty(password)) {
			args.add("-p");
			args.add(password);
		}
	}

	public String getStartMongoConsoleCommand() {
		StringBuilder connection = new StringBuilder();
		IServer server = getParent();
		Integer port = server.getPort();
		connection.append(server.getHost());
		if (port != null) {
			connection.append(":");
			connection.append(port.toString());
		}
		// Database
		connection.append("/");
		connection.append(getName());
		return connection.toString();
	}

}
