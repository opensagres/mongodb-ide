package fr.opensagres.nosql.ide.orientdb.core.model;

import java.net.URL;

import fr.opensagres.nosql.ide.core.model.AbstractServer;

public class OrientServer extends AbstractServer {

	public static final String TYPE_ID = "fr.opensagres.nosql.ide.orientdb.core";
	private URL url;

	public OrientServer(String name, URL url) {
		super(TYPE_ID, name);
		this.url = url;
	}

	public OrientServer(String id, String name, URL url) {
		super(TYPE_ID, id, name);
		this.url = url;
	}

	public String getURL() {
		return url.toString();
	}

	public void dispose() {

	}

	public String getLabel() {
		return getName() + " [" + getURL() + "] - " + getServerState();
	}

	public String getDatabaseName() {
		return url.getPath();
	}

	@Override
	protected void loadDatabases() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadDatabase(String databaseName) throws Exception {
		// TODO Auto-generated method stub

	}
}
