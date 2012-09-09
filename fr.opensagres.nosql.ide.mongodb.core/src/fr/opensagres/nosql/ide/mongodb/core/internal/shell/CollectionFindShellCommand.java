package fr.opensagres.nosql.ide.mongodb.core.internal.shell;

import com.mongodb.tools.driver.pagination.SortOrder;
import com.mongodb.tools.shell.ShellScriptBuilder;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.AbstractShellCommand;

public class CollectionFindShellCommand extends AbstractShellCommand {

	public CollectionFindShellCommand(IServer server, String collectionName,
			int pageNumber, int itemsPerPage, String sortName, SortOrder order) {
		super(server, SHELL_COLLECTION_FIND, ShellScriptBuilder.collectionFind(
				collectionName, pageNumber, itemsPerPage, sortName, order));
	}

}
