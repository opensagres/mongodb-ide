package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class DisconnectShellCommand extends AbstractShellCommand {

	public DisconnectShellCommand(IServer server) {
		super(server, SHELL_DISCONNECTED, ShellScriptBuilder.disconnect());
	}
}
