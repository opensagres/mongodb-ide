package fr.opensagres.nosql.ide.ui.internal.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.ui.internal.Messages;

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
			List<IServer> serversToRemove = null;
			List<IDatabase> databasesToRemove = null;
			List<ICollection> collectionsToRemove = null;
			List elementsToRemove = selection.toList();
			for (Object element : elementsToRemove) {
				if (element instanceof IServer) {
					if (serversToRemove == null) {
						serversToRemove = new ArrayList<IServer>();
					}
					serversToRemove.add((IServer) element);
				} else if (element instanceof IDatabase) {
					if (databasesToRemove == null) {
						databasesToRemove = new ArrayList<IDatabase>();
					}
					databasesToRemove.add((IDatabase) element);
				} else if (element instanceof Collection) {
					if (collectionsToRemove == null) {
						collectionsToRemove = new ArrayList<ICollection>();
					}
					collectionsToRemove.add((ICollection) element);
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
	private void deleteServers(List<IServer> serversToRemove) throws Exception {
		int nbServers = serversToRemove == null ? 0 : serversToRemove.size();
		if (nbServers > 0) {
			StringBuilder names = new StringBuilder();
			for (IServer server : serversToRemove) {
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
				for (IServer server : serversToRemove) {
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
	private void deleteDatabases(List<IDatabase> databasesToRemove)
			throws Exception {
		int nbDatabases = databasesToRemove == null ? 0 : databasesToRemove
				.size();
		if (nbDatabases > 0) {
			StringBuilder names = new StringBuilder();
			for (IDatabase database : databasesToRemove) {
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

				for (IDatabase database : databasesToRemove) {
					database.getServer().dropDatabase(database);
				}
			}
		}
	}

	/**
	 * Delete collections.
	 * 
	 * @param collectionsToRemove
	 */
	private void deleteCollections(List<ICollection> collectionsToRemove)
			throws Exception {
		int nbCollections = collectionsToRemove == null ? 0
				: collectionsToRemove.size();
		if (nbCollections > 0) {
			StringBuilder names = new StringBuilder();
			for (ICollection collection : collectionsToRemove) {
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
				for (ICollection collection : collectionsToRemove) {
					collection.getDatabase().getServer()
							.deleteCollection(collection);
				}
			}
		}
	}

}
