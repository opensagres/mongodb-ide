package com.mongodb.tools.shell.commands;

import com.mongodb.Mongo;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class DisconnectShellCommand extends AbstractShellCommand {

	public DisconnectShellCommand(Mongo mongo) {
		super(SHELL_DISCONNECTED, ShellScriptBuilder.disconnect(), mongo);
	}

	public void execute() {

	}
}
