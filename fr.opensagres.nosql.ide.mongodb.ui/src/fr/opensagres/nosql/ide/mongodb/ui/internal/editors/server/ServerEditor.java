package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.ui.editors.BasicModelFormEditor;

public class ServerEditor extends
		BasicModelFormEditor<ServerEditorInput, MongoServer> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.editors.server.ServerEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
	}

	@Override
	protected String getOverridePartName() {
		// modify the title of the editor with the name of the server.
		MongoServer server = getModelObject();
		if (server != null) {
			return server.getName();
		}
		return null;
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
