package fr.opensagres.nosql.ide.mongodb.core.model;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

import fr.opensagres.nosql.ide.core.model.AbstractServer;
import fr.opensagres.nosql.ide.core.model.ServerState;
import fr.opensagres.nosql.ide.mongodb.core.internal.Trace;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

public class MongoServer extends AbstractServer {

	public static final String TYPE_ID = "fr.opensagres.nosql.ide.mongodb.core";

	private MongoURI mongoURI;
	private String host;
	private Integer port;
	private Mongo mongo;

	public MongoServer(String id, String name, MongoURI mongoURI) {
		super(TYPE_ID, id, name);
		this.mongoURI = mongoURI;
	}

	public MongoServer(String name, MongoURI mongoURI) {
		super(TYPE_ID, name);
		this.mongoURI = mongoURI;
	}

	public MongoURI getMongoURI() {
		return mongoURI;
	}

	public String getHost() {
		computeHostAndPortIfNeeded();
		return host;
	}

	public Integer getPort() {
		computeHostAndPortIfNeeded();
		return port;
	}

	public String getDatabaseName() {
		return mongoURI.getDatabase();
	}

	private void computeHostAndPortIfNeeded() {
		if (host == null) {
			// host + port
			String hostAndPort = mongoURI.getHosts().get(0);
			int index = hostAndPort.indexOf(":");
			if (index > 0) {
				host = hostAndPort.substring(0, index);
				try {
					port = Integer.parseInt(hostAndPort.substring(index + 1,
							hostAndPort.length()));
				} catch (Throwable e) {
					Trace.trace(Trace.STRING_SEVERE, "Error parsing port", e);
				}
			} else {
				host = hostAndPort;
				port = null;
			}
		}
	}

	public String getUsername() {
		return mongoURI.getUsername();
	}

	public void setUsername(String username) {
		// TODO
	}

	public char[] getPassword() {
		return mongoURI.getPassword();
	}

	public void setPassword(char[] password) {
		// TODO
	}

	public String getLabel() {
		return getName() + " [" + mongoURI + "] - " + getServerState();
	}

	public String getURL() {
		return getMongoURI().toString();
	}

	@Override
	public void setServerState(ServerState serverState) {
		super.setServerState(serverState);
		if (serverState == ServerState.Stopped
				|| serverState == ServerState.Disconnected) {
			// close mongo.
			disposeMongo();
		}
	}

	@Override
	protected void loadDatabases() throws Exception {
		// Server connection doesn't contains database in the MongoURI
		// Display list of DB (works only if there is admin privilege
		// for this DB).
		Mongo mongo = getMongo();
		List<String> names = MongoShellCommandManager.getInstance().showDbs(
				this, mongo);
		for (String name : names) {
			Database database = new Database(name);
			super.addNode(database);
		}
	}

	@Override
	protected void loadDatabase(String databaseName) throws Exception {
		// Display just the database.
		Database database = new Database(databaseName);
		database.setParent(this);
		database.getDB().getCollectionNames();
		super.addNode(database);
	}

	public Mongo getMongo() throws UnknownHostException, MongoException {
		if (mongo == null) {
			mongo = MongoShellCommandManager.getInstance().connect(this,
					mongoURI);
		}
		return mongo;
	}

	public void dispose() {
		disposeMongo();
	}

	public void disposeMongo() {
		MongoShellCommandManager.getInstance().disconnect(this, mongo);
		mongo = null;
	}

}
