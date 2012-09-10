package fr.opensagres.nosql.ide.orientdb.core.model;

import java.net.URL;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.storage.OStorageProxy;

import fr.opensagres.nosql.ide.core.model.AbstractServer;
import fr.opensagres.nosql.ide.core.model.IDatabase;

public class OrientServer extends AbstractServer {

	public static final String TYPE_ID = "fr.opensagres.nosql.ide.orientdb.core";
	private URL url;
	private OServerAdmin serverAdmin;

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
		// see
		// http://code.google.com/p/orient/source/browse/trunk/tools/src/main/java/com/orientechnologies/orient/console/OConsoleDatabaseApp.java

	}

	@Override
	protected void loadDatabase(String databaseName) throws Exception {
		// see
		// http://code.google.com/p/orient/source/browse/trunk/tools/src/main/java/com/orientechnologies/orient/console/OConsoleDatabaseApp.java
		ODatabaseDocumentTx currentDatabase = new ODatabaseDocumentTx(
				"remote:127.0.0.1/demo");
		// if (currentDatabase == null)
		// throw new OException("Database " + iURL + " not found");

		// currentDatabase.registerListener(new OConsoleDatabaseListener(this));
		currentDatabase.open("admin", "admin");

		// currentDatabaseName = currentDatabase.getName();
		if (currentDatabase.getStorage() instanceof OStorageProxy)
			serverAdmin = new OServerAdmin(currentDatabase.getStorage()
					.getURL());

		Database database = new Database(databaseName);
		super.addNode(database);

	}

	@Override
	protected IDatabase doCreateDatabase(String databaseName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getPort() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDropDatabase(IDatabase database) throws Exception {
		// TODO Auto-generated method stub

	}
}
