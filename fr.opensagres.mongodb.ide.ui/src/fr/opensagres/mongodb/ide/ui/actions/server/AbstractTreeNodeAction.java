package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;

public abstract class AbstractTreeNodeAction extends SelectionProviderAction {
	protected Shell shell;

	public AbstractTreeNodeAction(ISelectionProvider selectionProvider,
			String text) {
		this(null, selectionProvider, text);
	}

	public AbstractTreeNodeAction(Shell shell,
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
	protected abstract boolean accept(Server server);

	/**
	 * Perform action on this server.
	 * 
	 * @param database
	 *            a database
	 */
	protected abstract void perform(Server server);

	/**
	 * Return true if this database can currently be acted on.
	 * 
	 * @return boolean
	 * @param database
	 *            a database
	 */
	protected abstract boolean accept(Database database);

	/**
	 * Perform action on this database.
	 * 
	 * @param database
	 *            a database
	 */
	protected abstract void perform(Database database);

	/**
	 * Return true if this collection can currently be acted on.
	 * 
	 * @return boolean
	 * @param collection
	 *            a collection
	 */
	protected abstract boolean accept(Collection collection);

	/**
	 * Perform action on this collection.
	 * 
	 * @param database
	 *            a database
	 */
	protected abstract void perform(Collection collection);

	/**
	 * Return true if this users can currently be acted on.
	 * 
	 * @return boolean
	 * @param users
	 *            a users
	 */
	protected abstract boolean accept(Users users);

	/**
	 * Perform action on this users.
	 * 
	 * @param database
	 *            a database
	 */
	protected abstract void perform(Users users);

	public void run() {
		Iterator iterator = getStructuredSelection().iterator();

		if (!iterator.hasNext())
			return;

		Object obj = iterator.next();
		if (obj instanceof Server) {
			Server server = (Server) obj;
			if (accept(server)) {
				perform(server);
			}
			selectionChanged(getStructuredSelection());
		} else if (obj instanceof Database) {
			Database database = (Database) obj;
			if (accept(database)) {
				perform(database);
			}
			selectionChanged(getStructuredSelection());
		} else if (obj instanceof Collection) {
			Collection collection = (Collection) obj;
			if (accept(collection)) {
				perform(collection);
			}
			selectionChanged(getStructuredSelection());
		} else if (obj instanceof Users) {
			Users users = (Users) obj;
			if (accept(users)) {
				perform(users);
			}
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
				if (accept(server)) {
					enabled = true;
				}
			} else if (obj instanceof Database) {
				Database database = (Database) obj;
				if (accept(database)) {
					enabled = true;
				}
			} else if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				if (accept(collection)) {
					enabled = true;
				}
			} else if (obj instanceof Users) {
				Users users = (Users) obj;
				if (accept(users)) {
					enabled = true;
				}
			} else {
				setEnabled(false);
				return;
			}
		}
		setEnabled(enabled);
	}

}
