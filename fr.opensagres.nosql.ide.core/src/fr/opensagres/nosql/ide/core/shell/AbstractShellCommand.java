package fr.opensagres.nosql.ide.core.shell;

import fr.opensagres.nosql.ide.core.model.IServer;

public abstract class AbstractShellCommand implements IShellCommand {

	private final IServer server;
	private final String command;
	private final int kind;

	public AbstractShellCommand(IServer server, int kind, String command) {
		this.server = server;
		this.kind = kind;
		this.command = command;
	}

	public IServer getServer() {
		return server;
	}

	public int getKind() {
		return kind;
	}

	public String getCommand() {
		return command;
	}

}
