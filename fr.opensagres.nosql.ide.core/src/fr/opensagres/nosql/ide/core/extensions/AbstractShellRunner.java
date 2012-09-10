package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.IDatabase;

public abstract class AbstractShellRunner implements IShellRunner {

	public boolean canSupport(IDatabase database) {
		if (database == null) {
			return false;
		}
		return database.getServer().hasRuntime();
	}

}
