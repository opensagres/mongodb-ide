package com.mongodb.tools.shell.commands;

import com.mongodb.Mongo;
import com.mongodb.tools.shell.IShellCommand;

public abstract class AbstractShellCommand implements IShellCommand {

	private final Mongo mongo;
	private final String command;
	private final int kind;

	public AbstractShellCommand(int kind, String command, Mongo mongo) {
		this.kind = kind;
		this.command = command;
		this.mongo = mongo;
	}

	public int getKind() {
		return kind;
	}

	public String getCommand() {
		return command;
	}

	public Mongo getMongo() {
		return mongo;
	}
}
