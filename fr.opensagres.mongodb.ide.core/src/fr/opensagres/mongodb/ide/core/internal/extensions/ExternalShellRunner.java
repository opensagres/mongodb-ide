package fr.opensagres.mongodb.ide.core.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.AbstractShellRunner;
import fr.opensagres.mongodb.ide.core.model.Database;

public class ExternalShellRunner extends AbstractShellRunner {

	public void startShell(Database database) throws Exception {
		// TODO : start cmd/sh shell.

		String cmd = database.getMongoConsoleCommand(true);
		CommandExecHelper.exec(cmd);

	}

	public void stopShell(Database database) throws Exception {
		// Do Nothing
	}

	public boolean canSupportStop() {
		return false;
	}

}
