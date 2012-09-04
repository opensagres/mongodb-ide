package fr.opensagres.nosql.ide.mongodb.core.shell;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.tools.driver.MongoInstanceManager;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommandManager;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.ConnectShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.DBAuthenticateShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.DisconnectShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.ShowCollectionsShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.ShowDbsShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.UseShellCommand;

public class MongoShellCommandManager extends AbstractShellCommandManager {

	private static final MongoShellCommandManager INSTANCE = new MongoShellCommandManager();

	public static MongoShellCommandManager getInstance() {
		return INSTANCE;
	}

	public Mongo connect(IServer server, MongoURI mongoURI)
			throws UnknownHostException, MongoException {
		Mongo mongo = MongoInstanceManager.getInstance().createMongo(mongoURI);
		// MongoDriverHelper.tryConnection(mongo);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ConnectShellCommand(server, mongoURI));
		}
		return mongo;
	}

	public void disconnect(IServer server, Mongo mongo) {
		if (mongo == null) {
			return;
		}
		MongoInstanceManager.getInstance().dispose(mongo);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new DisconnectShellCommand(server));
		}
	}

	public List<String> showDbs(IServer server, Mongo mongo) {
		List<String> names = mongo.getDatabaseNames();
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ShowDbsShellCommand(server, names));
		}
		return names;
	}

	public DB use(IServer server, Mongo mongo, String dbname) {
		DB db = mongo.getDB(dbname);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new UseShellCommand(server, dbname));
		}
		return db;
	}

	public boolean authenticate(IServer server, DB db, String username,
			char[] passwd) {
		boolean result = db.authenticate(username, passwd);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new DBAuthenticateShellCommand(server, username, passwd));
		}
		return result;
	}

	public Set<String> showCollections(IServer server, DB db) {
		Set<String> names = db.getCollectionNames();
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ShowCollectionsShellCommand(server));
		}
		return names;
	}

	public List<DBObject> getDBCollectionGetIndexes(IServer server,
			DBCollection dbCollection) {
		return dbCollection.getIndexInfo();
	}

}
