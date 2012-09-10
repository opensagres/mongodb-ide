package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class DropDatabaseShellCommand extends AbstractShellCommand {

	public DropDatabaseShellCommand(IServer server) {
		super(server, SHELL_DROP_DATABASE, ShellScriptBuilder.dropDatabase());
	}
}
