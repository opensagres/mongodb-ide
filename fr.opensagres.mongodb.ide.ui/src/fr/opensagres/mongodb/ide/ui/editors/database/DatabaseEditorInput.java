package fr.opensagres.mongodb.ide.ui.editors.database;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelEditorInput;

public class DatabaseEditorInput extends BasicModelEditorInput<Database> {

	public DatabaseEditorInput(Database database) {
		super(database);
	}
}
