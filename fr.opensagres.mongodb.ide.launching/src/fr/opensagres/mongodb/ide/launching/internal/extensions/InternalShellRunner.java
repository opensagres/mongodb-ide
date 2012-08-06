package fr.opensagres.mongodb.ide.launching.internal.extensions;

import fr.opensagres.mongodb.ide.core.extensions.AbstractShellRunner;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StartShellJob;
import fr.opensagres.mongodb.ide.launching.internal.jobs.StopShellJob;

public class InternalShellRunner extends AbstractShellRunner {

	public void startShell(Database database) {
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StartShellJob startJob = new StartShellJob(database);
		startJob.schedule();

	}

	public void stopShell(Database database) {
		// see bug 250999 - debug UI must be loaded before looking for debug
		// consoles
		org.eclipse.debug.ui.console.IConsole.class.toString();

		StopShellJob stopJob = new StopShellJob(database);
		stopJob.schedule();

	}

}
