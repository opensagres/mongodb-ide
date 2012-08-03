package com.mongodb.tools.shell;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.tools.driver.DBObjectHelper;
import com.mongodb.tools.driver.MongoInstanceManager;
import com.mongodb.tools.driver.pagination.Page;
import com.mongodb.tools.driver.pagination.PaginationHelper;
import com.mongodb.tools.driver.pagination.SortOrder;
import com.mongodb.tools.shell.commands.CollectionFindShellCommand;
import com.mongodb.tools.shell.commands.ConnectShellCommand;
import com.mongodb.tools.shell.commands.DBAuthenticateShellCommand;
import com.mongodb.tools.shell.commands.DisconnectShellCommand;
import com.mongodb.tools.shell.commands.GetSystemUsersShellCommand;
import com.mongodb.tools.shell.commands.ShowCollectionsShellCommand;
import com.mongodb.tools.shell.commands.ShowDbsShellCommand;
import com.mongodb.tools.shell.commands.UseShellCommand;

public class ShellCommandManager {

	private static final ShellCommandManager INSTANCE = new ShellCommandManager();

	public static ShellCommandManager getInstance() {
		return INSTANCE;
	}

	private ShellNotificationManager notificationManager;

	public Mongo connect(MongoURI mongoURI) throws UnknownHostException,
			MongoException {
		return connect(mongoURI, null);
	}

	public Mongo connect(MongoURI mongoURI, ShellContext shellContext)
			throws UnknownHostException, MongoException {
		Mongo mongo = MongoInstanceManager.getInstance().createMongo(mongoURI);
		// MongoDriverHelper.tryConnection(mongo);
		if (shellContext != null) {
			shellContext.setMongo(mongo);
		}
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ConnectShellCommand(mongoURI, mongo));
		}
		return mongo;
	}

	public void disconnect(Mongo mongo) {
		if (mongo == null) {
			return;
		}
		MongoInstanceManager.getInstance().dispose(mongo);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new DisconnectShellCommand(mongo));
		}
	}

	public void disconnect(ShellContext shellContext) {
		disconnect(shellContext.getMongo());
	}

	public List<String> showDbs(ShellContext shellContext) {
		return showDbs(shellContext.getMongo());
	}

	public List<String> showDbs(Mongo mongo) {
		List<String> names = mongo.getDatabaseNames();
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ShowDbsShellCommand(mongo, names));
		}
		return names;
	}

	public Set<String> showCollections(ShellContext shellContext) {
		return showCollections(shellContext.getDB());
	}

	public Set<String> showCollections(DB db) {
		Set<String> names = db.getCollectionNames();
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new ShowCollectionsShellCommand(db, names));
		}
		return names;
	}

	public DB use(String dbname, ShellContext shellContext) {
		Mongo mongo = shellContext.getMongo();
		DB db = use(dbname, mongo);
		shellContext.setDB(db);
		return db;
	}

	public DB use(String dbname, Mongo mongo) {
		DB db = mongo.getDB(dbname);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new UseShellCommand(mongo, db));
		}
		return db;
	}

	public boolean authenticate(DB db, String username, char[] passwd) {
		boolean result = db.authenticate(username, passwd);
		if (hasListeners()) {
			getShellNotificationManager()
					.broadcastChange(
							new DBAuthenticateShellCommand(db, username,
									passwd, result));
		}
		return result;
	}

	public List<DBObject> getDBCollectionGetIndexes(DBCollection dbCollection) {
		return dbCollection.getIndexInfo();
	}

	public Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage) {
		return paginate(collection, pageNumber, itemsPerPage, null, null);
	}

	public Page paginate(DBCollection collection, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		Page page = PaginationHelper.paginate(collection, pageNumber,
				itemsPerPage, sortName, order);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new CollectionFindShellCommand(collection, pageNumber,
							itemsPerPage, sortName, order));
		}
		return page;
	}

	public List<DBObject> getSystemUsers(DB db) {
		List<DBObject> users = DBObjectHelper.getSystemUsers(db);
		if (hasListeners()) {
			getShellNotificationManager().broadcastChange(
					new GetSystemUsersShellCommand(db, users));
		}
		return users;
	}

	private boolean hasListeners() {
		return (notificationManager != null && !notificationManager
				.hasNoListeners());
	}

	/**
	 * Adds the given shell state listener to this shell. Once registered, a
	 * listener starts receiving notification of state changes to this shell.
	 * The listener continues to receive notifications until it is removed. Has
	 * no effect if an identical listener is already registered.
	 * 
	 * @param listener
	 *            the shell listener
	 * @see #removeShellListener(IShellCommandListener)
	 */
	public void addShellListener(IShellCommandListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Shell listener cannot be null");
		// if (Trace.LISTENERS) {
		// Trace.trace(Trace.STRING_LISTENERS, "Adding shell listener "
		// + listener + " to " + this);
		// }
		getShellNotificationManager().addListener(listener);
	}

	/**
	 * Adds the given shell state listener to this shell. Once registered, a
	 * listener starts receiving notification of state changes to this shell.
	 * The listener continues to receive notifications until it is removed. Has
	 * no effect if an identical listener is already registered.
	 * 
	 * @param listener
	 *            the shell listener
	 * @param eventMask
	 *            the bit-wise OR of all event types of interest to the listener
	 * @see #removeShellListener(IShellCommandListener)
	 */
	public void addShellListener(IShellCommandListener listener, int eventMask) {
		if (listener == null)
			throw new IllegalArgumentException("Module cannot be null");
		// if (Trace.LISTENERS) {
		// Trace.trace(Trace.STRING_LISTENERS, "Adding shell listener "
		// + listener + " to " + this + " with eventMask " + eventMask);
		// }
		getShellNotificationManager().addListener(listener, eventMask);
	}

	/**
	 * Removes the given shell state listener from this shell. Has no effect if
	 * the listener is not registered.
	 * 
	 * @param listener
	 *            the listener
	 * @see #addShellListener(IShellCommandListener)
	 */
	public void removeShellListener(IShellCommandListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Shell listener cannot be null");
		// if (Trace.LISTENERS) {
		// Trace.trace(Trace.STRING_LISTENERS, "Removing shell listener "
		// + listener + " from " + this);
		// }
		getShellNotificationManager().removeListener(listener);
	}

	/**
	 * Returns the event notification manager.
	 * 
	 * @return the notification manager
	 */
	private ShellNotificationManager getShellNotificationManager() {
		if (notificationManager == null)
			notificationManager = new ShellNotificationManager();

		return notificationManager;
	}

	public ShellContext createContext() {
		return new ShellContext(this);
	}

}
