package fr.opensagres.nosql.ide.core.shell;

public class AbstractShellCommandManager implements IShellCommandListenerAware {

	private ShellNotificationManager notificationManager;

	protected boolean hasListeners() {
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
	protected ShellNotificationManager getShellNotificationManager() {
		if (notificationManager == null)
			notificationManager = new ShellNotificationManager();

		return notificationManager;
	}
}
