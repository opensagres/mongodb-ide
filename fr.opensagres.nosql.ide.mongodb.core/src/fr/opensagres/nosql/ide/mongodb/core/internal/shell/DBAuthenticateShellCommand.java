package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class DBAuthenticateShellCommand extends AbstractShellCommand {

	public DBAuthenticateShellCommand(IServer server, String username,
			char[] passwd) {
		super(server, SHELL_DB_AUTHENTICATE, ShellScriptBuilder.dbAuthenticate(
				username, passwd));
	}

}
