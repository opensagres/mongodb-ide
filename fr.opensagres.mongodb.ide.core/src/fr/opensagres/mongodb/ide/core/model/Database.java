package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.Platform;

public class Database extends TreeContainerNode<Server, Collection> {

	private static final String SEPARATOR = "____";
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

	private String computeId() {
		return getParent().getId() + SEPARATOR + name;
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
		DB db = getDB();
		Set<String> names = db.getCollectionNames();
		for (String name : names) {
			Collection collection = new Collection(name);
			super.addNode(collection);
		}
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
}
