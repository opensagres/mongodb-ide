package fr.opensagres.mongodb.ide.core.extensions;

import fr.opensagres.mongodb.ide.core.model.Database;

public abstract class AbstractShellRunner implements IShellRunner {

	public boolean canSupport(Database database) {
		if (database == null) {
			return false;
		}
		return database.getParent().hasRuntime();
	}

}
