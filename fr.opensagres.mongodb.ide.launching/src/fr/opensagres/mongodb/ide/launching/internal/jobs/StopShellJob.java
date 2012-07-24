package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.launching.internal.Messages;
import fr.opensagres.mongodb.ide.launching.internal.Trace;

public class StopShellJob extends DatabaseJob {

	public StopShellJob(Database database) {
		super(database, NLS.bind(Messages.jobStopping, database.getName()));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Database database = super.getDatabase();
		ILaunch launch = (ILaunch) database.getLaunch();
		if (launch != null) {
			try {
				launch.terminate();
			} catch (DebugException e) {
				Trace.trace(Trace.STRING_SEVERE, "", e);
			} finally {
				database.setLaunch(null);
			}
		}
		return Status.OK_STATUS;
	}

}
