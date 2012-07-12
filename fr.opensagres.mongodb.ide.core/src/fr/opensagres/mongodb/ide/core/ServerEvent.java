package fr.opensagres.mongodb.ide.core;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;

public class ServerEvent {

	private Server server;
	private ServerState state;
	private int kind;

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
