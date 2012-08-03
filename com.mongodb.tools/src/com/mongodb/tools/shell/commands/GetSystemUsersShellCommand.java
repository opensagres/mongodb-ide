package com.mongodb.tools.shell.commands;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class GetSystemUsersShellCommand extends AbstractShellCommand {

	private final List<DBObject> users;
	private DB db;

	public GetSystemUsersShellCommand(DB db, List<DBObject> users) {
		super(SHELL_USE, ShellScriptBuilder.systemUsers(), db.getMongo());
		this.users = users;
	}

	public DB getDB() {
		return db;
	}

	public void execute() {

	}
}
