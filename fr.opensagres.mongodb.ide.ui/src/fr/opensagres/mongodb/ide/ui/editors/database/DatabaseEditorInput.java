package fr.opensagres.mongodb.ide.ui.editors.database;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.editors.AbstractEditorInput;

public class DatabaseEditorInput extends AbstractEditorInput {

	private final Database database;

	public DatabaseEditorInput(Database database) {
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public String getName() {
		return getDatabase().getName();
	}

	public String getToolTipText() {
		return getDatabase().getLabel();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DatabaseEditorInput))
			return false;
		DatabaseEditorInput other = (DatabaseEditorInput) obj;
		if (database == null) {
			if (other.getDatabase() != null) {
				return false;
			}
			return true;
		}
		String databaseId = database.getId();
		if (databaseId == null) {
			if (other.getDatabase().getId() != null)
				return false;
		} else if (!databaseId.equals(other.getDatabase().getId()))
			return false;
		return true;
	}

}
