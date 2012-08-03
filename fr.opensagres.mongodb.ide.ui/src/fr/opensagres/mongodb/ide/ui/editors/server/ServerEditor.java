package fr.opensagres.mongodb.ide.ui.editors.server;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelFormEditor;

public class ServerEditor extends
		BasicModelFormEditor<ServerEditorInput, Server> {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.server.ServerEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
	}

	@Override
	protected String getOverridePartName() {
		// modify the title of the editor with the name of the server.
		Server server = getModelObject();
		if (server != null) {
			return server.getName();
		}
		return null;
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
