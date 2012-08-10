package fr.opensagres.mongodb.ide.ui.viewers.sort;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class BeanSortTreeColumnSelectionListener extends
		AbstractBeanSortColumnSelectionListener {

	public BeanSortTreeColumnSelectionListener(String propertyName,
			TreeViewer viewer) {
		super(propertyName, viewer);
	}

	public BeanSortTreeColumnSelectionListener(String propertyName,
			int sortDirection, TreeViewer viewer) {
		super(propertyName, sortDirection, viewer);
	}

	@Override
	protected void updateSortUI(SelectionEvent e) {
		// 1) Get tree column which fire this selection event
		TreeColumn treeColumn = (TreeColumn) e.getSource();
		// 2) Get the owner tree
		Tree tree = treeColumn.getParent();
		// 3) Modify the SWT Tree sort
		tree.setSortColumn(treeColumn);
		tree.setSortDirection(getSortDirection());
	}

}
