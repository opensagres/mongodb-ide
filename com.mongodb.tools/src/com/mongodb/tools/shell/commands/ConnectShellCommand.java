package com.mongodb.tools.shell.commands;

import com.mongodb.Mongo;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class ConnectShellCommand extends AbstractShellCommand {

	public ConnectShellCommand(String host, Integer port, Mongo mongo) {
		super(SHELL_CONNECTED, ShellScriptBuilder.connect(host, port), mongo);
	}

	public void execute() {

	}
	
	public void undo() {

	}
}
