package fr.opensagres.mongodb.ide.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class DeleteAction extends Action {

	private final TreeViewer viewer;

	public DeleteAction(TreeViewer viewer) {
		super.setText(Messages.DeleteAction_text);
		super.setToolTipText(Messages.DeleteAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DELETE_16));
		this.viewer = viewer;
	}

	public void run() {
		ITreeSelection selection = (ITreeSelection) viewer.getSelection();
		if (selection != null && !selection.isEmpty()) {
			List<Server> serversToRemove = null;
			List<Database> databasesToRemove = null;
			List<Collection> collectionsToRemove = null;
			List elementsToRemove = selection.toList();
			for (Object element : elementsToRemove) {
				if (element instanceof Server) {
					if (serversToRemove == null) {
						serversToRemove = new ArrayList<Server>();
						serversToRemove.add((Server) element);
					}
				} else if (element instanceof Database) {
					if (databasesToRemove == null) {
						databasesToRemove = new ArrayList<Database>();
						databasesToRemove.add((Database) element);
					}
				} else if (element instanceof Collection) {
					if (collectionsToRemove == null) {
						collectionsToRemove = new ArrayList<Collection>();
						collectionsToRemove.add((Collection) element);
					}
				}
			}

			// Delete servers
			try {
				deleteServers(serversToRemove);
			} catch (Throwable e) {
				StackTraceErrorDialog.openError(viewer.getControl().getShell(),
						"dialogTitle", "title", e);
			}

			// Delete databases
			try {
				deleteDatabases(databasesToRemove);
			} catch (Throwable e) {
				StackTraceErrorDialog.openError(viewer.getControl().getShell(),
						"dialogTitle", "title", e);
			}

			// Delete collections
			try {
				deleteCollections(collectionsToRemove);
			} catch (Throwable e) {
				StackTraceErrorDialog.openError(viewer.getControl().getShell(),
						"dialogTitle", "title", e);
			}
		}
	}

	/**
	 * Delete servers list.
	 * 
	 * @param serversToRemove
	 * @throws Exception
	 */
	private void deleteServers(List<Server> serversToRemove) throws Exception {
		int nbServers = serversToRemove == null ? 0 : serversToRemove.size();
		if (nbServers > 0) {
			StringBuilder names = new StringBuilder();
			for (Server server : serversToRemove) {
				if (names.length() > 0) {
					names.append(",");
				}
				names.append(server.getName());
			}

			if (MessageDialog.openConfirm(
					viewer.getControl().getShell(),
					Messages.DeleteAction_serverTitle,
					NLS.bind(Messages.DeleteAction_serverMessage,
							names.toString()))) {
				for (Server server : serversToRemove) {
					Platform.getServerManager().removeServer(server);
				}
			}
		}
	}

	/**
	 * Delete databases.
	 * 
	 * @param databasesToRemove
	 */
	private void deleteDatabases(List<Database> databasesToRemove)
			throws Exception {
		int nbDatabases = databasesToRemove == null ? 0 : databasesToRemove
				.size();
		if (nbDatabases > 0) {
			StringBuilder names = new StringBuilder();
			for (Database database : databasesToRemove) {
				if (names.length() > 0) {
					names.append(",");
				}
				names.append(database.getName());
			}

			if (MessageDialog.openConfirm(
					viewer.getControl().getShell(),
					Messages.DeleteAction_databaseTitle,
					NLS.bind(Messages.DeleteAction_databaseMessage,
							names.toString()))) {

				for (Database database : databasesToRemove) {
					database.getParent().dropDatabase(database);
				}
			}
		}
	}

	/**
	 * Delete collections.
	 * 
	 * @param collectionsToRemove
	 */
	private void deleteCollections(List<Collection> collectionsToRemove) {
		int nbCollections = collectionsToRemove == null ? 0
				: collectionsToRemove.size();
		if (nbCollections > 0) {
			StringBuilder names = new StringBuilder();
			for (Collection collection : collectionsToRemove) {
				if (names.length() > 0) {
					names.append(",");
				}
				names.append(collection.getName());
			}

			if (MessageDialog.openConfirm(
					viewer.getControl().getShell(),
					Messages.DeleteAction_collectionTitle,
					NLS.bind(Messages.DeleteAction_collectionMessage,
							names.toString()))) {
				for (Collection collection : collectionsToRemove) {
					collection.getDatabase().getParent()
							.deleteCollection(collection);
				}
			}
		}
	}

}
