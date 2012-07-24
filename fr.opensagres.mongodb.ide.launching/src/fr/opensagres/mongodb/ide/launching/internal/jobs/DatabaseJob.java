package fr.opensagres.mongodb.ide.launching.internal.jobs;

import org.eclipse.core.runtime.jobs.Job;

import fr.opensagres.mongodb.ide.core.model.Database;

public abstract class DatabaseJob extends Job {
	private final Database database;

	public DatabaseJob(Database database, String name) {
		super(name);
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

}
