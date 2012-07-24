package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.launching.internal.Messages;

public class StopShellJob extends DatabaseJob {

	public StopShellJob(Database database) {
		super(database, NLS.bind(Messages.jobStopping, database.getName()));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return Status.OK_STATUS;
	}

}
