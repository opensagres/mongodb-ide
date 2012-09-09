package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.collection;

import java.net.UnknownHostException;
import java.util.Locale;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.nebula.widgets.pagination.tree.PageableTree;
import org.eclipse.nebula.widgets.pagination.tree.forms.FormPageableTree;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.Users;
import fr.opensagres.nosql.ide.mongodb.core.model.Collection;
import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.core.model.Document;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Trace;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.MongoPageResultContentProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectContentProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectKeyColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectPageResultLoader;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectTypeColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectValueColumnLabelProvider;
import fr.opensagres.nosql.ide.ui.ServerUI;
import fr.opensagres.nosql.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.nosql.ide.ui.viewers.ViewerHelper;

public class DocumentsPage extends AbstractToolbarFormPage {

	public static final String ID = "documents";

	public DocumentsPage(CollectionEditor editor) {
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
		pageableTree.getViewer().getTree()
				.setLayoutData(new GridData(GridData.FILL_BOTH));

		// 2) Initialize the tree viewer + SWT Tree
		TreeViewer viewer = pageableTree.getViewer();
		viewer.setContentProvider(DBObjectContentProvider.getInstance());
		// viewer.setLabelProvider(new LabelProvider());

		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		// 3) Create Tree columns with sort of paginated list.
		createColumns(viewer);

		// 3) Set current page to 0 to refresh the tree
		pageableTree.setPageLoader(new DBObjectPageResultLoader<DBObject>(
				getColllection().getServer(), dbCollection));
		pageableTree.setCurrentPage(0);

		initialize(viewer);
	}

	public Collection getColllection() {
		return ((CollectionEditor) getEditor()).getModelObject();
	}

	private static void createColumns(final TreeViewer viewer) {

		// First column is for the Key
		TreeViewerColumn col = ViewerHelper.createColumn(viewer,
				Messages.columnKey, 200,
				DBObjectKeyColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("name"));

		// Second column is for the Value
		col = ViewerHelper.createColumn(viewer, Messages.columnValue, 300,
				DBObjectValueColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("address.name"));

		// Third column is for the Type
		col = ViewerHelper.createColumn(viewer, Messages.columnType, 100,
				DBObjectTypeColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTreeColumnSelectionListener("address.name"));
		//
		// TableColumnLayout layout = new TableColumnLayout();
		// comp.setLayout( layout );
		// // 4
		// layout.setColumnData( column1, new ColumnWeightData( 30 ) );
		// layout.setColumnData( column2, new ColumnWeightData( 60 ) );

	}

	private void initialize(final TreeViewer viewer) {
		// Open Server, Database, Collection editor when user double click on
		// the node
		viewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				try {
					IStructuredSelection sel = (IStructuredSelection) event
							.getSelection();
					Object data = sel.getFirstElement();
					if (data instanceof BasicDBObject) {
						ServerUI.openEditor(new Document((BasicDBObject) data,
								getColllection()));
					}
				} catch (Exception e) {
					if (Trace.SEVERE) {
						Trace.trace(Trace.STRING_SEVERE,
								"Could not open document", e);
					}
				}
			}
		});

		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		final Shell shell = getSite().getShell();
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(shell, mgr, viewer);
			}
		});
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
		getSite().setSelectionProvider(viewer);
	}

	protected void fillContextMenu(Shell shell, IMenuManager menu,
			TreeViewer viewer) {
		// get selection but avoid no selection or multiple selection
		IServer server = null;
		Database database = null;
		Collection collection = null;
		Users users = null;
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (selection.size() == 1) {
			Object obj = selection.getFirstElement();
			if (obj instanceof IServer) {
				server = (IServer) obj;
			} else if (obj instanceof Database) {
				database = (Database) obj;
			} else if (obj instanceof Collection) {
				collection = (Collection) obj;
			} else if (obj instanceof Users) {
				users = (Users) obj;
			}
		}
	}
}
