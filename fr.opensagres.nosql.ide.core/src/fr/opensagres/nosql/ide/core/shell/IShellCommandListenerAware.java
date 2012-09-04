package fr.opensagres.nosql.ide.core.shell;

public interface IShellCommandListenerAware {

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
	public void addShellListener(IShellCommandListener listener);

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
	public void addShellListener(IShellCommandListener listener, int eventMask);

	/**
	 * Removes the given shell state listener from this shell. Has no effect if
	 * the listener is not registered.
	 * 
	 * @param listener
	 *            the listener
	 * @see #addShellListener(IShellCommandListener)
	 */
	public void removeShellListener(IShellCommandListener listener);
}
