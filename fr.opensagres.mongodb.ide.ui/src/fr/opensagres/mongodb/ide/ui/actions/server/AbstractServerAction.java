package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;

import fr.opensagres.mongodb.ide.core.model.Server;

public abstract class AbstractServerAction extends SelectionProviderAction {
	protected Shell shell;

	public AbstractServerAction(ISelectionProvider selectionProvider,
			String text) {
		this(null, selectionProvider, text);
	}

	public AbstractServerAction(Shell shell,
			ISelectionProvider selectionProvider, String text) {
		super(selectionProvider, text);
		this.shell = shell;
		setEnabled(false);
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	public boolean accept(Server server) {
		return true;
	}

	/**
	 * Perform action on this server.
	 * 
	 * @param server
	 *            a server
	 */
	public abstract void perform(Server server);

	public void run() {
		Iterator iterator = getStructuredSelection().iterator();

		if (!iterator.hasNext())
			return;

		Object obj = iterator.next();
		if (obj instanceof Server) {
			Server server = (Server) obj;
			if (accept(server))
				perform(server);
			selectionChanged(getStructuredSelection());
		}
	}

	/**
	 * Update the enabled state.
	 * 
	 * @param sel
	 *            a selection
	 */
	public void selectionChanged(IStructuredSelection sel) {
		if (sel.isEmpty()) {
			setEnabled(false);
			return;
		}
		boolean enabled = false;
		Iterator iterator = sel.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj instanceof Server) {
				Server server = (Server) obj;
				if (accept(server))
					enabled = true;
			} else {
				setEnabled(false);
				return;
			}
		}
		setEnabled(enabled);
	}

}
