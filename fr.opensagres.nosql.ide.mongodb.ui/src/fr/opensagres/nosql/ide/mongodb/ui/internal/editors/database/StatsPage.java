package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.core.model.stats.CollectionStats;
import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsAvgObjColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsContentProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsCountColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsIndexSizeColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsNameColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsPaddingColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsSizeColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.stats.StatsStorageColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.nosql.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.nosql.ide.ui.viewers.ViewerHelper;

public class StatsPage extends AbstractToolbarFormPage {

	public static final String ID = "stats";
	private TreeViewer viewer;

	public StatsPage(DatabaseEditor editor) {
		super(editor, ID, Messages.StatsPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_STATS_16);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout());

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout());
		left.setLayoutData(new GridData(GridData.FILL_BOTH));

		createStatsSection(left, toolkit);

		initialize();
	}

	private void createStatsSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.DatabaseEditor_OverviewPage_Stats_desc);
		section.setText(Messages.DatabaseEditor_OverviewPage_Stats_title);
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		glayout.numColumns = 1;
		sbody.setLayout(glayout);
		sbody.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Create TreeView of stats
		final Tree treeStats = toolkit.createTree(sbody, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		treeStats.setLayoutData(new GridData(GridData.FILL_BOTH));

		viewer = new TreeViewer(treeStats);
		viewer.setContentProvider(StatsContentProvider.getInstance());

		treeStats.setHeaderVisible(true);
		treeStats.setLinesVisible(true);

		// 3) Create Tree columns with sort of paginated list.
		createColumns(viewer);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

	}

	private void initialize() {
		Database database = ((DatabaseEditor) getEditor()).getModelObject();
		try {
			List<CollectionStats> collectionStats = database.createStats();
			viewer.setInput(collectionStats);
		} catch (Exception e) {
			StackTraceErrorDialog.openError(getSite().getShell(),
					"dialogTitle", "title", e);
		}
	}

	private static void createColumns(final TreeViewer viewer) {

		// Name collection column
		ViewerHelper.createColumn(viewer, Messages.columnName, 150,
				StatsNameColumnLabelProvider.getInstance(),
				CollectionStats.NAME_PROPERTY);

		// Count column
		ViewerHelper.createColumn(viewer, Messages.columnCount, 50,
				StatsCountColumnLabelProvider.getInstance(),
				CollectionStats.COUNT_PROPERTY);

		// Size column
		ViewerHelper.createColumn(viewer, Messages.columnSize, 50,
				StatsSizeColumnLabelProvider.getInstance(),
				CollectionStats.SIZE_PROPERTY);

		// Storage column
		ViewerHelper.createColumn(viewer, Messages.columnStorage, 60,
				StatsStorageColumnLabelProvider.getInstance(),
				CollectionStats.STORAGE_PROPERTY);

		// Index Size column
		ViewerHelper.createColumn(viewer, Messages.columnIndexSize, 50,
				StatsIndexSizeColumnLabelProvider.getInstance(),
				CollectionStats.TOTAL_INDEX_SIZE_PROPERTY);

		// AvgObj column
		ViewerHelper.createColumn(viewer, Messages.columnAvgObj, 50,
				StatsAvgObjColumnLabelProvider.getInstance(),
				CollectionStats.AVGOBJ_PROPERTY);

		// Padding column
		ViewerHelper.createColumn(viewer, Messages.columnPadding, 60,
				StatsPaddingColumnLabelProvider.getInstance(),
				CollectionStats.PADDING_PROPERTY);

	}
}
