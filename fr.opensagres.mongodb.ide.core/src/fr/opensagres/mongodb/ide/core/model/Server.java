package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.Platform;

public class Server extends TreeContainerNode<Server, TreeSimpleNode> {

	private String name;
	private String host;
	private Integer port;
	private String userName;
	private String password;
	private ServerStatus serverStatus;

	public Server(String name, String host, Integer port) {
		setName(name);
		setHost(host);
		setPort(port);
		this.serverStatus = ServerStatus.Inactive;
	}

	private Mongo mongo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getLabel() {
		return name + " [" + host + ":" + port + "] - " + serverStatus;
	}

	@Override
	public NodeType getType() {
		return NodeType.Server;
	}

	@Override
	protected void doGetChildren() throws Exception {
		Mongo mongo = getMongo();
		List<String> names = mongo.getDatabaseNames();
		for (String name : names) {
			Database database = new Database(name);
			super.addNode(database);
		}
	}

	public Mongo getMongo() throws UnknownHostException, MongoException {
		if (mongo == null) {
			mongo = Platform.getMongoInstanceManager().createMongo(host, port);
			if (serverStatus != ServerStatus.Started) {
				this.serverStatus = ServerStatus.Connected;
			}
		}
		return mongo;
	}

	public void dispose() {
		Platform.getMongoInstanceManager().dispose(mongo);
	}

	public ServerStatus getServerStatus() {
		return serverStatus;
	}

	public void start() throws Exception {
		try {
			this.serverStatus = ServerStatus.Starting;
			Platform.getServerLauncherManager().start(this);
			this.serverStatus = ServerStatus.Started;
		} finally {
			if (this.serverStatus != ServerStatus.Starting) {
				this.serverStatus = ServerStatus.Error;
			}
		}
	}

	public void stop() throws Exception {
		Platform.getServerLauncherManager().stop(this);
	}

}
