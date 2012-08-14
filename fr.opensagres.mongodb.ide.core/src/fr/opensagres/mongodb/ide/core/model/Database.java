package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.stats.CollectionListStats;
import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class Database extends TreeContainerNode<Server> {

	private String name;
	private String id;
	private DB db;
	private Object launch;
	private boolean alreadyAuthenticated;
	private final CollectionsCategory collectionsCategory;

	public Database(String name) {
		super();
		this.name = name;
		this.alreadyAuthenticated = false;
		this.collectionsCategory = new CollectionsCategory();
		// force compute of static children nodes.
		getChildren();
	}

	@Override
	protected void setParent(Server parent) {
		super.setParent(parent);
		this.id = computeId();
	}

	public static String[] getIds(String databaseId) {
		int index = databaseId.lastIndexOf(SEPARATOR);
		String serverId = databaseId.substring(0, index);
		String databaseName = databaseId.substring(index + SEPARATOR.length(),
				databaseId.length());
		return new String[] { serverId, databaseName };
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.id = computeId();
	}

	@Override
	public NodeType getType() {
		return NodeType.Database;
	}

	public String getLabel() {
		return getName();
	}

	@Override
	protected void doGetChildren() throws Exception {
		super.addNode(collectionsCategory);
		// Collections folder
		super.addNode(new StoredJavascriptCategory());
		// GridFS folder
		super.addNode(new GridFSCategory());
		// Users
		super.addNode(new Users());
	}

	public DB getDB() throws UnknownHostException, MongoException {
		boolean databaseChanged = getParent().selectDatabase(this);
		if (db == null || databaseChanged) {
			db = getInternalDB();
		}
		return db;
	}

	private DB getInternalDB() throws UnknownHostException, MongoException {
		Server server = getParent();
		// 1) use databseName
		DB db = getShellCommandManager().use(getName(), server.getMongo());
		String username = server.getUsername();
		// 2) authenticate if needed
		if (StringUtils.isNotEmpty(username) && !alreadyAuthenticated) {
			getShellCommandManager().authenticate(db, username,
					server.getPassword());
			alreadyAuthenticated = true;
		}
		return db;
	}

	public String getId() {
		return id;
	}

	public Object getLaunch() {
		return launch;
	}

	public void setLaunch(Object launch) {
		this.launch = launch;
	}

	public boolean canStartShell() {
		return getParent().hasRuntime() && launch == null;
	}

	public boolean canStopShell() {
		return launch != null;
	}

	public List<DBObject> getUsers() throws UnknownHostException,
			MongoException {
		return getShellCommandManager().getSystemUsers(getDB());
	}

	public String getMongoConsoleCommand(boolean withBaseDir) {

		StringBuilder connection = new StringBuilder("");
		if (!withBaseDir) {
			connection.append("mongo");
		} else {
			// mongo.exe
			MongoRuntime runtime = getParent().getRuntime();
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
		Server server = getParent();
		String username = server.getUsername();
		if (StringUtils.isNotEmpty(username)) {
			args.add("-u");
			args.add(username);
		}
		char[] password = server.getPassword();
		if (password != null && password.length > 0) {
			args.add("-p");
			args.add(String.valueOf(password));
		}
	}

	public String getStartMongoConsoleCommand() {
		StringBuilder connection = new StringBuilder();
		Server server = getParent();
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

	public List<Collection> getCollections() {
		return collectionsCategory.getCollections();
	}

	public CollectionsCategory getCollectionsCategory() {
		return collectionsCategory;
	}

	public List<CollectionStats> createStats() throws UnknownHostException,
			MongoException {
		List<Collection> collections = getCollections();
		CollectionListStats collectionStats = new CollectionListStats(
				collections.size());
		for (Collection collection : collections) {
			collectionStats.addCollection(collection);
		}
		return collectionStats;
	}
}
