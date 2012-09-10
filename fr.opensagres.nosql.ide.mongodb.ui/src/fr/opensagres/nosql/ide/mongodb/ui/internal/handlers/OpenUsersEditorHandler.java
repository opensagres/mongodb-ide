package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.core.model.Users;
import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database.DatabaseEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database.DatabaseEditorInput;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database.UsersPage;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenUsersEditorHandler extends OpenEditorHandler<Users> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenUsersEditorHandler";

	@Override
	protected String getEditorId() {
		return DatabaseEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(Users users) {
		DatabaseEditorInput input = new DatabaseEditorInput((Database)users.getParent());
		input.setActivePageIdOnLoad(UsersPage.ID);
		return input;
	}

}
