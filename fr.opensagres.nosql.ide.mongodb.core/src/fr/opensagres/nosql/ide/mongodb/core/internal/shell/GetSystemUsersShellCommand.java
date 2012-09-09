package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class GetSystemUsersShellCommand extends AbstractShellCommand {

	public GetSystemUsersShellCommand(IServer server) {
		super(server, SHELL_USE, ShellScriptBuilder.systemUsers());
	}

}
