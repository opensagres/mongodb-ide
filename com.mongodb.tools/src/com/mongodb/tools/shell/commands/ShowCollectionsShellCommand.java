package com.mongodb.tools.shell.commands;

import java.util.Set;

import com.mongodb.DB;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class ShowCollectionsShellCommand extends AbstractShellCommand {

	private Set<String> collectionNames;
	private DB db;

	public ShowCollectionsShellCommand(DB db, Set<String> collectionNames) {
		super(SHELL_SHOW_COLLECTIONS, ShellScriptBuilder.showCollections(), db
				.getMongo());
		this.db = db;
		this.collectionNames = collectionNames;
	}

	public Set<String> getCollectionNames() {
		return collectionNames;
	}

	public DB getDB() {
		return db;
	}

	public void execute() {

	}
}
