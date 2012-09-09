package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database.DatabaseEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database.DatabaseEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenDatabaseEditorHandler extends OpenEditorHandler<Database> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenDatabaseEditorHandler";

	@Override
	protected String getEditorId() {
		return DatabaseEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(Database server) {
		return new DatabaseEditorInput(server);
	}

}
