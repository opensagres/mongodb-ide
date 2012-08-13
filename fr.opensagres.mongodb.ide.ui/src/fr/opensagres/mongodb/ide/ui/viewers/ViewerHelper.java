package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;

import fr.opensagres.mongodb.ide.ui.viewers.sort.BeanSortTableColumnSelectionListener;
import fr.opensagres.mongodb.ide.ui.viewers.sort.BeanSortTreeColumnSelectionListener;

public class ViewerHelper {

	public static TreeViewerColumn createColumn(TreeViewer viewer,
			String title, int bound, CellLabelProvider labelProvider) {
		return createColumn(viewer, title, bound, labelProvider, null);
	}

	public static TreeViewerColumn createColumn(TreeViewer viewer,
			String title, int bound, CellLabelProvider labelProvider,
			String sortName) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer,
				SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		viewerColumn.setLabelProvider(labelProvider);

		if (sortName != null) {
			viewerColumn.getColumn().addSelectionListener(
					new BeanSortTreeColumnSelectionListener(sortName, viewer));
		}
		return viewerColumn;
	}

	public static TableViewerColumn createColumn(TableViewer viewer,
			String title, int bound, CellLabelProvider labelProvider) {
		return createColumn(viewer, title, bound, labelProvider, null);
	}

	public static TableViewerColumn createColumn(TableViewer viewer,
			String title, int bound, CellLabelProvider labelProvider,
			String sortName) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		viewerColumn.setLabelProvider(labelProvider);
		if (sortName != null) {
			viewerColumn.getColumn().addSelectionListener(
					new BeanSortTableColumnSelectionListener(sortName, viewer));
		}
		return viewerColumn;
	}
}
