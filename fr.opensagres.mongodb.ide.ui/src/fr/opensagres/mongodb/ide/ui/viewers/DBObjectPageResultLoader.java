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
package fr.opensagres.mongodb.ide.ui.viewers;

import java.util.List;

import org.eclipse.nebula.widgets.pagination.IPageLoader;
import org.eclipse.nebula.widgets.pagination.PageableController;

import com.mongodb.DBCollection;
import com.mongodb.tools.driver.pagination.Page;
import com.mongodb.tools.driver.pagination.PaginationHelper;

/**
 * Implementation of {@link IPageLoader} with java {@link List}.
 * 
 */
public class DBObjectPageResultLoader<T> implements IPageLoader<Page<T>> {

	private final DBCollection dbCollection;

	public DBObjectPageResultLoader(DBCollection collection) {
		this.dbCollection = collection;
	}

	public Page<T> loadPage(PageableController controller) {
		int pageNumber = controller.getCurrentPage();
		int itemsPerPage = controller.getPageSize();
		return PaginationHelper.paginate(dbCollection, pageNumber, itemsPerPage);
	}

}
