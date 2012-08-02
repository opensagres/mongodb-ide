package com.mongodb.tools.shell.commands;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class DBAuthenticateShellCommand extends AbstractShellCommand {

	public DBAuthenticateShellCommand(MongoURI mongoURI, Mongo mongo) {
		super(SHELL_CONNECTED, ShellScriptBuilder.connect(mongoURI), mongo);
	}

	public DBAuthenticateShellCommand(DB db, String username, char[] passwd,
			boolean result) {
		super(SHELL_DB_AUTHENTICATE, ShellScriptBuilder.dbAuthenticate(username, passwd), db.getMongo());
	}

	public void execute() {

	}

	public void undo() {

	}
}
