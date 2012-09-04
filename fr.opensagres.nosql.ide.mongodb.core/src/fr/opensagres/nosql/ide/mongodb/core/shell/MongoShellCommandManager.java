package fr.opensagres.nosql.ide.mongodb.core.shell;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.tools.driver.MongoInstanceManager;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommandManager;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.ConnectShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.DisconnectShellCommand;
import fr.opensagres.nosql.ide.mongodb.core.internal.shell.ShowDbsShellCommand;

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

}
