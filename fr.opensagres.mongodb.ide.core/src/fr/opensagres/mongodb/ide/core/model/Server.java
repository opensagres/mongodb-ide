package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.MongoDriverHelper;
import com.mongodb.tools.driver.pagination.ShellCommandManagerTest;
import com.mongodb.tools.shell.ShellCommandManager;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.internal.Activator;
import fr.opensagres.mongodb.ide.core.internal.Messages;
import fr.opensagres.mongodb.ide.core.internal.ServerNotificationManager;
import fr.opensagres.mongodb.ide.core.internal.Trace;

public class Server extends TreeContainerNode<Server, TreeSimpleNode> implements
		ISchedulingRule {

	private final String id;
	private String name;
	private String host;
	private Integer port;
	private String userName;
	private String password;
	private ServerState serverState;
	private MongoRuntime runtime;
	private Mongo mongo;
	private ServerNotificationManager notificationManager;
	private Map dataCache = new HashMap();

	public Server(String name, String host, Integer port) {
		this(String.valueOf(System.currentTimeMillis()), name, host, port);
	}

	public Server(String id, String name, String host, Integer port) {
		this.id = id;
		setName(name);
		setHost(host);
		setPort(port);
		this.serverState = ServerState.Stopped;
	}

	public String getId() {
		return id;
	}

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
		return name + " [" + host + ":" + port + "] - " + serverState;
	}

	@Override
	public NodeType getType() {
		return NodeType.Server;
	}

	@Override
	protected void doGetChildren() throws Exception {
		if (isConnected()) {
			Mongo mongo = getMongo();
			List<String> names = mongo.getDatabaseNames();
			for (String name : names) {
				Database database = new Database(name);
				super.addNode(database);
			}
		}
	}

	public Mongo getMongo() throws UnknownHostException, MongoException {
		if (mongo == null) {
			mongo = ShellCommandManager.getInstance().connect(host, port);
		}
		return mongo;
	}

	public void dispose() {
		disposeMongo();
	}

	public void disposeMongo() {
		ShellCommandManager.getInstance().disconnect(mongo);
		mongo = null;
	}

	public ServerState getServerState() {
		return serverState;
	}

	public void setServerState(ServerState serverState) {
		this.serverState = serverState;
		// remove tree item of the server node
		clearNodes(true);
		// fire events
		fireServerStateChangeEvent();
		if (serverState == ServerState.Stopped) {
			// close mongo.
			disposeMongo();
		}
	}

	public void start() throws Exception {
		if (Platform.hasServerLauncherManager()) {
			Platform.getServerLauncherManager().start(this);
		}
	}

	public void stop(boolean force) throws Exception {
		if (Platform.hasServerLauncherManager()) {
			Platform.getServerLauncherManager().stop(this, force);
		}
	}

	public void setRuntime(MongoRuntime runtime) {
		this.runtime = runtime;
	}

	public MongoRuntime getRuntime() {
		return runtime;
	}

	public boolean hasRuntime() {
		return runtime != null;
	}

	public <T> T getData(Class<T> key) {
		return (T) dataCache.get(key);
	}

	public <T> void setData(T data) {
		dataCache.put(data.getClass(), data);
	}

	/**
	 * Returns true if the server is in a state that it can be stopped.
	 * 
	 * @return boolean
	 */
	public IStatus canStop() {
		if (getServerState() == ServerState.Stopped
				|| getServerState() == ServerState.Stopping
				|| getServerState() == ServerState.Disconnected)
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					Messages.errorStopAlreadyStopped, null);

		return Status.OK_STATUS;
	}

	/**
	 * Adds the given server state listener to this server. Once registered, a
	 * listener starts receiving notification of state changes to this server.
	 * The listener continues to receive notifications until it is removed. Has
	 * no effect if an identical listener is already registered.
	 * 
	 * @param listener
	 *            the server listener
	 * @see #removeServerListener(IServerListener)
	 */
	public void addServerListener(IServerListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Module cannot be null");
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS, "Adding server listener "
					+ listener + " to " + this);
		}
		getServerNotificationManager().addListener(listener);
	}

	/**
	 * Adds the given server state listener to this server. Once registered, a
	 * listener starts receiving notification of state changes to this server.
	 * The listener continues to receive notifications until it is removed. Has
	 * no effect if an identical listener is already registered.
	 * 
	 * @param listener
	 *            the server listener
	 * @param eventMask
	 *            the bit-wise OR of all event types of interest to the listener
	 * @see #removeServerListener(IServerListener)
	 */
	public void addServerListener(IServerListener listener, int eventMask) {
		if (listener == null)
			throw new IllegalArgumentException("Module cannot be null");
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS, "Adding server listener "
					+ listener + " to " + this + " with eventMask " + eventMask);
		}
		getServerNotificationManager().addListener(listener, eventMask);
	}

	/**
	 * Removes the given server state listener from this server. Has no effect
	 * if the listener is not registered.
	 * 
	 * @param listener
	 *            the listener
	 * @see #addServerListener(IServerListener)
	 */
	public void removeServerListener(IServerListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Module cannot be null");
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS, "Removing server listener "
					+ listener + " from " + this);
		}
		getServerNotificationManager().removeListener(listener);
	}

	/**
	 * Fire a server listener state change event.
	 */
	protected void fireServerStateChangeEvent() {
		if (Trace.LISTENERS) {
			Trace.trace(Trace.STRING_LISTENERS,
					"->- Firing server state change event: " + getName() + ", "
							+ getServerState() + " ->-");
		}

		if (notificationManager == null || notificationManager.hasNoListeners())
			return;

		getServerNotificationManager().broadcastChange(
				new ServerEvent(ServerEvent.SERVER_CHANGE
						| ServerEvent.STATE_CHANGE, this, getServerState()));
	}

	/**
	 * Returns the event notification manager.
	 * 
	 * @return the notification manager
	 */
	private ServerNotificationManager getServerNotificationManager() {
		if (notificationManager == null)
			notificationManager = new ServerNotificationManager();

		return notificationManager;
	}

	public boolean contains(ISchedulingRule rule) {
		return rule instanceof Server;
	}

	public boolean isConflicting(ISchedulingRule rule) {
		return this.equals(rule);
	}

	public int getStopTimeout() {
		// TODO Auto-generated method stub
		return 1;
	}

	public boolean isConnected() {
		return serverState == ServerState.Started
				|| serverState == ServerState.Connected;
	}

	public void connect() throws UnknownHostException, MongoException {
		// Try to connect
		MongoDriverHelper.tryConnection(getMongo());
		// Connection is OK, update the server state as connected.
		setServerState(ServerState.Connected);
	}

	public void disconnect() {
		disposeMongo();
		setServerState(ServerState.Disconnected);
	}

	public boolean canStartServer() {
		if (!Platform.hasServerLauncherManager()) {
			return false;
		}
		if (!hasRuntime()) {
			return false;
		}
		return true;
	}

	public Database findDatabase(String databaseName) {
		List<TreeSimpleNode> children = getChildren();
		for (TreeSimpleNode child : children) {
			if (NodeType.Database.equals(child.getType())) {
				if (databaseName.equals(((Database) child).getName())) {
					return (Database) child;
				}
			}
		}
		return null;
	}

	public void unlock() {
		Mongo mongo = null;
		try {
			mongo = getMongo();
		} catch (Throwable e) {
		}
		if (mongo != null) {
			mongo.unlock();
		}
	}

	public boolean isLocked() {
		try {
			Mongo mongo = getMongo();
			return mongo.isLocked();
		} catch (Throwable e) {
			return false;
		}
	}
}
