package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.AbstractShellRunner;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.utils.CommandExecHelper;
import fr.opensagres.nosql.ide.mongodb.core.model.Database;

public class ExternalShellRunner extends AbstractShellRunner {

	public void startShell(IDatabase database) throws Exception {
		// TODO : start cmd/sh shell.

		String cmd = ((Database) database).getMongoConsoleCommand(true);
		CommandExecHelper.exec(cmd);

	}

	public void stopShell(IDatabase database) throws Exception {
		// Do Nothing
	}

	public boolean canSupportStop() {
		return false;
	}

}
