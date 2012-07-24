package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.jobs.Job;

import fr.opensagres.mongodb.ide.core.model.Server;

public abstract class ServerJob extends Job {
	private final Server server;

	public ServerJob(Server server, String name) {
		super(name);
		this.server = server;
	}

	// public boolean belongsTo(Object family) {
	// return ServerUtil.SERVER_JOB_FAMILY.equals(family);
	// }

	public Server getServer() {
		return server;
	}

}
