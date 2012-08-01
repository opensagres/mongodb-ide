package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.Platform;

public class Database extends TreeContainerNode<Server> {

	private String name;
	private String id;
	private DB db;
	private Object launch;

	public Database(String name) {
		super();
		this.name = name;
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

	@Override
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
		if (db == null) {
			Server server = getParent();
			db = server.getMongo().getDB(getName());
		}
		return db;

	}

	public void startShell() {
		if (Platform.hasServerLauncherManager()) {
			Platform.getServerLauncherManager().startShell(this);
		}
	}

	public void stopShell() {
		if (Platform.hasServerLauncherManager()) {
			Platform.getServerLauncherManager().stopShell(this);
		}
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
}
