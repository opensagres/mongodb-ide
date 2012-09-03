package fr.opensagres.nosql.ide.mongodb.core.model;

import com.mongodb.MongoURI;

import fr.opensagres.nosql.ide.core.model.AbstractServer;
import fr.opensagres.nosql.ide.mongodb.core.internal.Trace;

public class MongoServer extends AbstractServer {

	public static final String TYPE_ID = "fr.opensagres.nosql.ide.mongodb.core";

	private MongoURI mongoURI;
	private String host;
	private Integer port;

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

	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doGetChildren() throws Exception {
		// TODO Auto-generated method stub

	}

}
