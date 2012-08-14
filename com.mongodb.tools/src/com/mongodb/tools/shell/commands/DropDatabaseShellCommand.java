package com.mongodb.tools.shell.commands;

import com.mongodb.DB;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class DropDatabaseShellCommand extends AbstractShellCommand {

	public DropDatabaseShellCommand(DB db) {
		super(SHELL_DROP_DATABASE, ShellScriptBuilder.dropDatabase(), db.getMongo());
	}

	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
