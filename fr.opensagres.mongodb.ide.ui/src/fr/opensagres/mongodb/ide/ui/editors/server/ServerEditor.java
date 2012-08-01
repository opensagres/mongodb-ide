package fr.opensagres.mongodb.ide.ui.editors.server;

import org.eclipse.ui.PartInitException;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.editors.AbstractFormEditor;
import fr.opensagres.mongodb.ide.ui.internal.Trace;

public class ServerEditor extends AbstractFormEditor {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.server.ServerEditor";

	@Override
	protected void addPages() {
		try {
			super.addPage(new OverviewPage(this));
		} catch (PartInitException e) {
			Trace.trace(Trace.STRING_SEVERE,
					"Error while adding page in the editor ", e);
		}
	}

	@Override
	protected void createPages() {
		// create pages
		super.createPages();
		// modify the title of the editor with the name of the server.
		Server server = getServer();
		if (server != null) {
			super.setPartName(server.getName());
		}
	}

	public Server getServer() {
		return ((ServerEditorInput) getEditorInput()).getServer();
	}
}
