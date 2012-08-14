package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;

public class ServerEvent {

	private Server server;
	private ServerState state;
	private int kind;
	private Database database;
	private Collection collection;

	/**
	 * For notification when the state has changed.
	 * <p>
	 * This kind is mutually exclusive with <code>PUBLISH_STATE_CHANGE</code>
	 * and <code>RESTART_STATE_CHANGE</code>.
	 * </p>
	 * 
	 * @see #getKind()
	 */
	public static final int STATE_CHANGE = 0x0001;

	/**
	 * For event on server changes. This kind is mutually exclusive with
	 * <code>MODULE_CHANGE</code>.
	 * 
	 * @see #getKind()
	 */
	public static final int SERVER_CHANGE = 0x0010;
	public static final int SERVER_SAVED = 0x0011;

	public static final int DATABASE_CHANGE = 0x0020;
	public static final int DATABASE_CREATED = 0x0012;
	public static final int DATABASE_DROPPED = 0x0014;

	public static final int COLLECTION_CHANGE = 0x0030;
	public static final int COLLECTION_CREATED = 0x0032;

	/**
	 * Returns the server involved in the change event.
	 * 
	 * @return the server involved in the change event.
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Create a new server event for server change events.
	 * 
	 * @param kind
	 *            the kind of the change. (<code>XXX_CHANGE</code>). If the kind
	 *            does not include the <code>SERVER_CHANGE</code> kind, the
	 *            SERVER_CHANGE will be added automatically. constants declared
	 *            on {@link ServerEvent}
	 * @param server
	 *            the server that the server event takes place
	 * @param state
	 *            the server state after the change (<code>STATE_XXX</code>)
	 *            constants declared on {@link IServer}
	 * @param publishingState
	 *            the server publishing state after the change (
	 *            <code>PUBLISH_STATE_XXX</code>) constants declared on
	 *            {@link IServer}
	 * @param restartState
	 *            get the server restart state after the server is restart
	 *            needed property change event
	 * @param status
	 *            the server status after the change
	 */
	public ServerEvent(int kind, Server server, ServerState state) {
		this.kind = kind |= SERVER_CHANGE;
		this.server = server;
		this.state = state;

		if (server == null)
			throw new IllegalArgumentException(
					"Server parameter must not be null");
	}

	public ServerEvent(int kind, Database database) {
		this.kind = kind |= DATABASE_CHANGE;
		this.database = database;
		if (database == null)
			throw new IllegalArgumentException(
					"Database parameter must not be null");
		this.server = database.getParent();
	}

	public ServerEvent(int kind, Collection collection) {
		this.kind = kind |= COLLECTION_CHANGE;
		this.collection = collection;
		this.database = collection.getDatabase();
		if (database == null)
			throw new IllegalArgumentException(
					"Database parameter must not be null");
		this.server = database.getParent();
	}

	/**
	 * Returns the kind of the server event.
	 * <p>
	 * This kind can be used to test whether this event is a server event or
	 * module event by using the following code (the example is checking for the
	 * server event): ((getKind() & SERVER_CHANGE) != 0)
	 * 
	 * @return the kind of the change (<code>XXX_CHANGE</code> constants
	 *         declared on {@link ServerEvent}
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Get the state after the change that triggers this server event. If this
	 * event is of the SERVER_CHANGE kind, then the state is the server state.
	 * If this event is of the MODULE_CHANGE kind, then the state is the module
	 * state.
	 * 
	 * @return the server state after the change (<code>STATE_XXX</code>)
	 *         constants declared on {@link ServerState}
	 */
	public ServerState getState() {
		return state;
	}

	public Database getDatabase() {
		return database;
	}

	public Collection getCollection() {
		return collection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "<Server-Event" + " id=" + this.hashCode() + " kind="
				+ getKind() + " server=" + getServer() + " state=" + getState()
				+ ">";
	}
}
