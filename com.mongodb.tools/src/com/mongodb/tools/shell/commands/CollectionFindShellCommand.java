package com.mongodb.tools.shell.commands;

import com.mongodb.DBCollection;
import com.mongodb.tools.driver.pagination.SortOrder;
import com.mongodb.tools.shell.ShellScriptBuilder;

public class CollectionFindShellCommand extends AbstractShellCommand {

	public CollectionFindShellCommand(DBCollection collection, int pageNumber,
			int itemsPerPage, String sortName, SortOrder order) {
		super(SHELL_COLLECTION_FIND, ShellScriptBuilder
				.collectionFind(collection.getName(), pageNumber, itemsPerPage,
						sortName, order), collection.getDB().getMongo());
	}

	public void execute() {
		// TODO Auto-generated method stub

	}

}
