package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database;

import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.ui.editors.BasicModelEditorInput;

public class DatabaseEditorInput extends BasicModelEditorInput<Database> {

	public DatabaseEditorInput(Database database) {
		super(database);
	}
}
