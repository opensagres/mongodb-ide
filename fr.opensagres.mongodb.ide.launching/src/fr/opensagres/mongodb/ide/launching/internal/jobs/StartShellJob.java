package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.launching.internal.Messages;
import fr.opensagres.mongodb.ide.launching.internal.ProgressUtil;
import fr.opensagres.mongodb.ide.launching.internal.ServerLauncherManager;
import fr.opensagres.mongodb.ide.launching.internal.Trace;

public class StartShellJob extends DatabaseJob {

	public StartShellJob(Database database) {
		super(database, NLS.bind(Messages.jobStarting, database.getName()));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor = ProgressUtil.getMonitorFor(monitor);
		Database database = super.getDatabase();
		try {
			ILaunchConfiguration launchConfig = ServerLauncherManager.getLaunchConfiguration(
					database, true, monitor);
			if (launchConfig != null) {
				ILaunch launch = launchConfig.launch(
						ILaunchManager.RUN_MODE, monitor); // , true); -
				// causes
				// workspace
				// lock
				database.setLaunch(launch);
			}

			if (Trace.FINEST) {
				Trace.trace(Trace.STRING_FINEST, "Launch: "
						+ database.getLaunch());
			}
		} catch (CoreException e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error starting server "
						+ database.toString(), e);
			}
			return e.getStatus();
		}
		return Status.OK_STATUS;
	}
}
