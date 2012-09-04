package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import java.util.List;

import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class ShowDbsShellCommand extends AbstractShellCommand {

	public ShowDbsShellCommand(IServer server, List<String> databaseNames) {
		super(server, SHELL_SHOW_DBS, ShellScriptBuilder.showDbs());
	}

}
