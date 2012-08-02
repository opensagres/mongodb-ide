package com.mongodb.tools.shell.commands;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class ConnectShellCommand extends AbstractShellCommand {

	public ConnectShellCommand(MongoURI mongoURI, Mongo mongo) {
		super(SHELL_CONNECTED, ShellScriptBuilder.connect(mongoURI), mongo);
	}

	public void execute() {

	}
	
	public void undo() {

	}
}
