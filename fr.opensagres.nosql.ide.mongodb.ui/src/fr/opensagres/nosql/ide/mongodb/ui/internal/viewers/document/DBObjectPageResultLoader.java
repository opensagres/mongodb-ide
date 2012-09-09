/*******************************************************************************
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document;

import java.util.List;

import org.eclipse.nebula.widgets.pagination.IPageLoader;
import org.eclipse.nebula.widgets.pagination.PageableController;

import com.mongodb.DBCollection;
import com.mongodb.tools.driver.pagination.Page;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

/**
 * Implementation of {@link IPageLoader} with java {@link List}.
 * 
 */
public class DBObjectPageResultLoader<T> implements IPageLoader<Page<T>> {

	private final IServer server;
	private final DBCollection dbCollection;

	public DBObjectPageResultLoader(IServer server, DBCollection collection) {
		this.server = server;
		this.dbCollection = collection;
	}

	public Page<T> loadPage(PageableController controller) {
		int pageNumber = controller.getCurrentPage();
		int itemsPerPage = controller.getPageSize();
		return MongoShellCommandManager.getInstance().paginate(server,
				dbCollection, pageNumber, itemsPerPage);
	}

}
