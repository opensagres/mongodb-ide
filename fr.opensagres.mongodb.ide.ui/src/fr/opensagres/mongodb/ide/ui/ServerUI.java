package fr.opensagres.mongodb.ide.ui;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.editors.collection.CollectionEditor;
import fr.opensagres.mongodb.ide.ui.editors.collection.CollectionEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.database.DatabaseEditor;
import fr.opensagres.mongodb.ide.ui.editors.database.DatabaseEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.server.ServerEditor;
import fr.opensagres.mongodb.ide.ui.editors.server.ServerEditorInput;
import fr.opensagres.mongodb.ide.ui.internal.Activator;
import fr.opensagres.mongodb.ide.ui.internal.Trace;

public class ServerUI {

	/**
	 * Open the given server with the server editor.
	 * 
	 * @param server
	 */
	public static void editServer(Server server) {
		if (server == null)
			return;

		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		try {
			ServerEditorInput input = new ServerEditorInput(server);
			page.openEditor(input, ServerEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error opening server editor",
						e);
			}
		}
	}

	/**
	 * Open the given database with the database editor.
	 * 
	 * @param database
	 */
	public static void editDatabase(Database database) {
		if (database == null)
			return;

		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		try {
			DatabaseEditorInput input = new DatabaseEditorInput(database);
			page.openEditor(input, DatabaseEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error opening database editor", e);
			}
		}
	}

	/**
	 * Open the given collection with the collection editor.
	 * 
	 * @param collection
	 */
	public static void editCollection(Collection collection) {
		if (collection == null)
			return;

		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		try {
			CollectionEditorInput input = new CollectionEditorInput(collection);
			page.openEditor(input, CollectionEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error opening collection editor", e);
			}
		}
	}

}