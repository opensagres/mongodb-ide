package fr.opensagres.mongodb.ide.ui.editors.collection;

import java.net.UnknownHostException;
import java.util.Locale;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.nebula.widgets.pagination.tree.PageableTree;
import org.eclipse.nebula.widgets.pagination.tree.forms.FormPageableTree;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectKeyColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectPageResultLoader;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectTypeColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectValueColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.MongoPageResultContentProvider;

public class DocumentsPage extends AbstractToolbarFormPage {

	public static final String ID = "documents";

	public DocumentsPage(FormEditor editor) {
		super(editor, ID, Messages.DocumentsPage_title);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout());

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout());
		left.setLayoutData(new GridData(GridData.FILL_BOTH));
		//

		DBCollection dbCollection = null;
		try {
			dbCollection = getColllection().getDBCollection();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 1) Create pageable tree with 10 items per page
		// This SWT Component create internally a SWT Tree+JFace TreeViewer
		int pageSize = 50;
		PageableTree pageableTree = new FormPageableTree(left, SWT.NONE,
				SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, toolkit,
				pageSize, MongoPageResultContentProvider.getInstance(),
				FormPageableTree.getDefaultPageRendererTopFactory(),
				FormPageableTree.getDefaultPageRendererBottomFactory());
		pageableTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		pageableTree.setLocale(Locale.ENGLISH);
		pageableTree.getViewer().getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 2) Initialize the tree viewer + SWT Tree
		TreeViewer viewer = pageableTree.getViewer();
		viewer.setContentProvider(DBObjectContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider());

		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		// 3) Create Tree columns with sort of paginated list.
		createColumns(viewer);

		// 3) Set current page to 0 to refresh the tree
		pageableTree.setPageLoader(new DBObjectPageResultLoader<DBObject>(
				dbCollection, getColllection().getShellCommandManager()));
		pageableTree.setCurrentPage(0);

	}

	public Collection getColllection() {
		return ((CollectionEditor) getEditor()).getColllection();
	}

	private static void createColumns(final TreeViewer viewer) {

		// First column is for the Key
		TreeViewerColumn col = createTreeViewerColumn(viewer, Messages.columnKey, 200);
		col.setLabelProvider(DBObjectKeyColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("name"));

		// Second column is for the Value
		col = createTreeViewerColumn(viewer, Messages.columnValue, 300);
		col.setLabelProvider(DBObjectValueColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("address.name"));

		// Third column is for the Type
		col = createTreeViewerColumn(viewer, Messages.columnType, 100);
		col.setLabelProvider(DBObjectTypeColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("address.name"));

	}

	private static TreeViewerColumn createTreeViewerColumn(TreeViewer viewer,
			String title, int bound) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer,
				SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
