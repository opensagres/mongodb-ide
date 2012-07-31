package com.mongodb.tools.shell.commands;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class UseShellCommand extends AbstractShellCommand {

	private final String name;
	private DB db;

	public UseShellCommand(Mongo mongo, DB db) {
		super(SHELL_USE, ShellScriptBuilder.use(db.getName()), mongo);
		this.name = db.getName();
	}

	public DB getDB() {
		return db;
	}

	public String getName() {
		return name;
	}

	public void execute() {

	}
}
