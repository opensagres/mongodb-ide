package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class Database extends TreeContainerNode<Server> {

	private String name;
	private String id;
	private DB db;
	private Object launch;
	private boolean alreadyAuthenticated;

	public Database(String name) {
		super();
		this.name = name;
		this.alreadyAuthenticated = false;
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
		// Collections folder
		super.addNode(new CollectionsCategory());
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
}
