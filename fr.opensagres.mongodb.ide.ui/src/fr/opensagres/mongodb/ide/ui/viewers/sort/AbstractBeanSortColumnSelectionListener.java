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
package fr.opensagres.mongodb.ide.ui.viewers.sort;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * 
 * Abstract class to sort a widget (table tree etc...) column by using the
 * attached pagination controller of the SWT parent (table tree...).
 * 
 */
public abstract class AbstractBeanSortColumnSelectionListener extends
		SelectionAdapter {

	private BeanViewerComparator currentComparator = null;
	private final ColumnViewer viewer;
	/** property name used to sort **/
	private final String sortPropertyName;
	/** the sort direction **/
	private int sortDirection;

	/**
	 * Constructor with property name and default sort (SWT.NONE).
	 * 
	 * @param propertyName
	 *            the sort property name.
	 */

	public AbstractBeanSortColumnSelectionListener(String propertyName,
			ColumnViewer viewer) {
		this(propertyName, SWT.NONE, viewer);
	}

	/**
	 * Constructor with property name and sort direction.
	 * 
	 * @param propertyName
	 *            the sort property name.
	 * @param sortDirection
	 *            the sort direction {@link SWT.UP}, {@link SWT.DOWN}.
	 * @param controller
	 *            the controller to update when sort is applied.
	 */
	public AbstractBeanSortColumnSelectionListener(String propertyName,
			int sortDirection, ColumnViewer viewer) {
		this.sortPropertyName = propertyName;
		this.sortDirection = sortDirection;
		this.viewer = viewer;
		if (viewer.getComparator() == null) {
			currentComparator = new BeanViewerComparator();
			viewer.setComparator(currentComparator);
		} else {
			if (viewer.getComparator() instanceof BeanViewerComparator) {
				currentComparator = (BeanViewerComparator) viewer
						.getComparator();
			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		sortDirection = sortDirection == SWT.DOWN ? SWT.UP : SWT.DOWN;
		currentComparator.setSortDirection(sortDirection);
		currentComparator.setSortPropertyName(sortPropertyName);
		// 5) Modify the sort of the page controller
		doSort();
		// 6) Modify the SWT Table sort
		updateSortUI(e);
	}

	private void doSort() {
		try {
			viewer.getControl().setRedraw(false);
			viewer.refresh();
		} finally {
			viewer.getControl().setRedraw(true);
		}
	}

	/**
	 * Returns the property name used to sort.
	 * 
	 * @return the sort property name.
	 */
	public String getSortPropertyName() {
		return sortPropertyName;
	}

	/**
	 * Returns the sort direction {@link SWT.UP}, {@link SWT.DOWN}.
	 * 
	 * @return
	 */
	public int getSortDirection() {
		return sortDirection;
	}

	/**
	 * Sort the column od the parent of the sorted column (ex Table for
	 * TableColumn, Tree for TreeColumn).
	 * 
	 * @param e
	 */
	protected abstract void updateSortUI(SelectionEvent e);

}
