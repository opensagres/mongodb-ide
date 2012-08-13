package fr.opensagres.mongodb.ide.ui;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.mongodb.DBObject;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Document;
import fr.opensagres.mongodb.ide.core.model.GridFSBucket;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.ui.editors.AbstractEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.collection.CollectionEditor;
import fr.opensagres.mongodb.ide.ui.editors.collection.CollectionEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.database.DatabaseEditor;
import fr.opensagres.mongodb.ide.ui.editors.database.DatabaseEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.database.UsersPage;
import fr.opensagres.mongodb.ide.ui.editors.document.DocumentEditor;
import fr.opensagres.mongodb.ide.ui.editors.document.DocumentEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.gridfs.GridFSEditor;
import fr.opensagres.mongodb.ide.ui.editors.gridfs.GridFSEditorInput;
import fr.opensagres.mongodb.ide.ui.editors.server.ServerEditor;
import fr.opensagres.mongodb.ide.ui.editors.server.ServerEditorInput;
import fr.opensagres.mongodb.ide.ui.internal.Activator;
import fr.opensagres.mongodb.ide.ui.internal.Trace;

public class ServerUI {

	private static final String[] LOCALHOSTS = new String[] { "localhost",
			"127.0.0.1" };

	private static final String[] DEFAULT_PORTS = new String[] { "27017" };

	public static String[] getLocalhosts() {
		return LOCALHOSTS;
	}

	public static String[] getDefaultPorts() {
		return DEFAULT_PORTS;
	}

	/**
	 * Open the given server with the server editor.
	 * 
	 * @param server
	 */
	public static void editServer(Server server) {
		if (server == null)
			return;

		try {
			ServerEditorInput input = new ServerEditorInput(server);
			openEditor(input, ServerEditor.ID);
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
		try {
			DatabaseEditorInput input = new DatabaseEditorInput(database);
			openEditor(input, DatabaseEditor.ID);
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
		try {
			CollectionEditorInput input = new CollectionEditorInput(collection);
			openEditor(input, CollectionEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error opening collection editor", e);
			}
		}
	}

	/**
	 * Open the given users with the users editor.
	 * 
	 * @param users
	 */
	public static void editUsers(Users users) {
		if (users == null)
			return;

		editUsers(users.getParent());
	}

	/**
	 * Open the given users with the users editor.
	 * 
	 * @param users
	 */
	public static void editUsers(Database database) {
		if (database == null)
			return;
		try {
			DatabaseEditorInput input = new DatabaseEditorInput(database);
			// select users tab.
			input.setActivePageIdOnLoad(UsersPage.ID);
			openEditor(input, DatabaseEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error opening users editor",
						e);
			}
		}
	}

	/**
	 * Open the given DB Object with the document editor.
	 * 
	 * @param document
	 */
	public static void editDocument(DBObject dbObject, Collection collection) {
		if (dbObject == null)
			return;
		editDocument(new Document(dbObject, collection));
	}

	/**
	 * Open the given document with the document editor.
	 * 
	 * @param document
	 */
	public static void editDocument(Document document) {
		if (document == null)
			return;

		try {
			DocumentEditorInput input = new DocumentEditorInput(document);
			openEditor(input, DocumentEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error opening document editor", e);
			}
		}
	}

	/**
	 * Open the given gridfs with the gridfs editor.
	 * 
	 * @param gridfs
	 */
	public static void editGridFS(GridFSBucket gridfs) {
		if (gridfs == null)
			return;

		try {
			GridFSEditorInput input = new GridFSEditorInput(gridfs);
			openEditor(input, GridFSEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error opening gridfs editor",
						e);
			}
		}
	}

	private static void openEditor(AbstractEditorInput input, String editorId)
			throws PartInitException {
		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorPart part = page.openEditor(input, editorId);
		String activePageIdOnLoad = input.getActivePageIdOnLoad();
		if (StringUtils.isNotEmpty(activePageIdOnLoad)) {
			((FormEditor) part).setActivePage(activePageIdOnLoad);
		}
	}
}
