package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.MongoURI;
import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class ConnectShellCommand extends AbstractShellCommand {

	public ConnectShellCommand(IServer server, MongoURI mongoURI) {
		super(server, SHELL_CONNECTED, ShellScriptBuilder.connect(mongoURI));
	}

}
