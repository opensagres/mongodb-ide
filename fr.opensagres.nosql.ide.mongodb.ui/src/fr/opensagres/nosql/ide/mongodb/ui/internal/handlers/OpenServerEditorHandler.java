package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server.ServerEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server.ServerEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenServerEditorHandler extends OpenEditorHandler<MongoServer> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenServerEditorHandler";

	@Override
	protected String getEditorId() {
		return ServerEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(MongoServer server) {
		return new ServerEditorInput(server);
	}

}
