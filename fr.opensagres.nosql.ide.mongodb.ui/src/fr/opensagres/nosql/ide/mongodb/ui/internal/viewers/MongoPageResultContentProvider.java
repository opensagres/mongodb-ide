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
package fr.opensagres.nosql.ide.mongodb.ui.internal.viewers;

import java.util.List;

import org.eclipse.nebula.widgets.pagination.IPageContentProvider;
import org.eclipse.nebula.widgets.pagination.PageableController;

import com.mongodb.tools.driver.pagination.Page;

/**
 * Implementation of {@link IPageContentProvider} to retrieves pagination
 * information (total elements and paginated list) from the pagination structure
 * {@link PageResult}.
 * 
 */
public class MongoPageResultContentProvider implements IPageContentProvider {

	private static final IPageContentProvider INSTANCE = new MongoPageResultContentProvider();

	/**
	 * Returns the singleton of {@link MongoPageResultContentProvider}.
	 * 
	 * @return
	 */
	public static IPageContentProvider getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.nebula.widgets.pagination.IPageContentProvider#createController
	 * (int)
	 */
	public PageableController createController(int pageSize) {
		return new PageableController(pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.nebula.widgets.pagination.IPageContentProvider#getTotalElements
	 * (java.lang.Object)
	 */
	public long getTotalElements(Object page) {
		return ((Page<?>) page).getTotalElements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.nebula.widgets.pagination.IPageContentProvider#getPaginatedList
	 * (java.lang.Object)
	 */
	public List<?> getPaginatedList(Object page) {
		return ((Page<?>) page).getContent();
	}

}
