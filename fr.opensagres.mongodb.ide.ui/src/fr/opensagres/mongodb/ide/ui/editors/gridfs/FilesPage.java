package fr.opensagres.mongodb.ide.ui.editors.gridfs;

import java.net.UnknownHostException;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.pagination.table.PageableTable;
import org.eclipse.nebula.widgets.pagination.table.forms.FormPageableTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.GridFSBucket;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.mongodb.ide.ui.viewers.DBObjectContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.MongoPageResultContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.ViewerHelper;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSChunkSizeColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSContentTypeColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSFilenameColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSFilesPageResultLoader;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSMD5ColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSSizeColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.gridfs.GridFSUploadedColumnLabelProvider;

public class FilesPage extends AbstractToolbarFormPage<GridFSEditor> {

	public static final String ID = "files";

	private TableViewer viewer;

	public FilesPage(GridFSEditor editor) {
		super(editor, ID, Messages.FilesPage_title);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout());

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout());
		left.setLayoutData(new GridData(GridData.FILL_BOTH));

		createFilesSection(left, toolkit);
	}

	private void createFilesSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.GridFSEditor_FilesPage_Files_desc);
		section.setText(Messages.GridFSEditor_FilesPage_Files_title);
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		sbody.setLayout(glayout);
		sbody.setLayoutData(new GridData(GridData.FILL_BOTH));

		// 1) Create pageable tree with 10 items per page
		// This SWT Component create internally a SWT Table+JFace TableViewer
		int pageSize = 50;
		PageableTable pageableTable = new FormPageableTable(sbody, SWT.NONE,
				SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, toolkit,
				pageSize, MongoPageResultContentProvider.getInstance(),
				FormPageableTable.getDefaultPageRendererTopFactory(),
				FormPageableTable.getDefaultPageRendererBottomFactory());
		pageableTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		pageableTable.setLocale(Locale.ENGLISH);
		pageableTable.getViewer().getTable()
				.setLayoutData(new GridData(GridData.FILL_BOTH));

		// 2) Initialize the table viewer + SWT Table
		TableViewer viewer = pageableTable.getViewer();
		viewer.setContentProvider(DBObjectContentProvider.getInstance());
		// viewer.setLabelProvider(new LabelProvider());

		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		// table.setLinesVisible(true);

		// 3) Create Table columns with sort of paginated list.
		createColumns(viewer);

		GridFSBucket gridFS = getEditor().getModelObject();
		// 3) Set current page to 0 to refresh the tree
		try {
			pageableTable.setPageLoader(new GridFSFilesPageResultLoader(gridFS
					.getDBGridFS(), gridFS.getShellCommandManager()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageableTable.setCurrentPage(0);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	private static void createColumns(final TableViewer viewer) {

		// Filename column
		ViewerHelper.createColumn(viewer, Messages.columnFilename, 200,
				GridFSFilenameColumnLabelProvider.getInstance());

		// Size column
		ViewerHelper.createColumn(viewer, Messages.columnSize, 50,
				GridFSSizeColumnLabelProvider.getInstance());

		// Chunk Size column
		ViewerHelper.createColumn(viewer, Messages.columnChunkSize, 70,
				GridFSChunkSizeColumnLabelProvider.getInstance());

		// Uploaded column
		ViewerHelper.createColumn(viewer, Messages.columnUploaded, 120,
				GridFSUploadedColumnLabelProvider.getInstance());

		// Content Type column
		ViewerHelper.createColumn(viewer, Messages.columnContentType, 80,
				GridFSContentTypeColumnLabelProvider.getInstance());

		// MD5 column
		ViewerHelper.createColumn(viewer, Messages.columnMD5, 70,
				GridFSMD5ColumnLabelProvider.getInstance());

	}
}
