package fr.opensagres.nosql.ide.orientdb.core.model;

import fr.opensagres.nosql.ide.core.model.AbstractServer;

public class OrientServer extends AbstractServer {

	public static final String TYPE_ID = "fr.opensagres.nosql.ide.orientdb.core";
	private String url;

	public OrientServer(String name, String url) {
		super(TYPE_ID, name);
		this.url = url;
	}

	public OrientServer(String id, String name, String url) {
		super(TYPE_ID, id, name);
		this.url = url;
	}

	public String getURL() {
		return url;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doGetChildren() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getLabel() {
		return getName() + " [" + url + "] - " + getServerState();
	}
}
