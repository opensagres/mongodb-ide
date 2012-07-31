package com.mongodb.tools.shell.commands;

import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class ShowDbsShellCommand extends AbstractShellCommand {

	private List<String> databaseNames;

	public ShowDbsShellCommand(Mongo mongo, List<String> databaseNames) {
		super(SHELL_SHOW_DBS, ShellScriptBuilder.showDbs(), mongo);
		this.databaseNames = databaseNames;
	}

	public List<String> getDatabaseNames() {
		return databaseNames;
	}

	public void execute() {

	}
}
