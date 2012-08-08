package fr.opensagres.mongodb.ide.ui.editors.database;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.stats.CollectionStats;
import fr.opensagres.mongodb.ide.ui.FormLayoutFactory;
import fr.opensagres.mongodb.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.mongodb.ide.ui.viewers.StatsContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.StatsCountColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.StatsNameColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.StatsSizeColumnLabelProvider;
import fr.opensagres.mongodb.ide.ui.viewers.ViewerHelper;

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
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

		Composite bottom = toolkit.createComposite(body);
		bottom.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false,
				2));
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		bottom.setLayoutData(data);
		createStatsSection(bottom, toolkit);

		initialize();
	}

	private void createStatsSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.databaseEditorOvervieStatsDescription);
		section.setText(Messages.databaseEditorOverviewStatsSection);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

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

		// treeStats.addListener(SWT.PaintItem, new Listener() {
		// int[] percents = new int[] { 50, 30, 5, 15 };
		//
		// public void handleEvent(Event event) {
		// if (event.index == 1) {
		// GC gc = event.gc;
		// TreeItem item = (TreeItem) event.item;
		// int index = treeStats.indexOf(item);
		// int percent = percents[index];
		// Color foreground = gc.getForeground();
		// Color background = gc.getBackground();
		// Display display = treeStats.getDisplay();
		// gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		// gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
		// int width = (100 - 1) * percent / 100;//(column2.getWidth() - 1) *
		// percent / 100;
		// gc.fillGradientRectangle(event.x, event.y, width,
		// event.height, true);
		// Rectangle rect2 = new Rectangle(event.x, event.y,
		// width - 1, event.height - 1);
		// gc.drawRectangle(rect2);
		// gc.setForeground(display
		// .getSystemColor(SWT.COLOR_LIST_FOREGROUND));
		// String text = percent + "%";
		// Point size = event.gc.textExtent(text);
		// int offset = Math.max(0, (event.height - size.y) / 2);
		// gc.drawText(text, event.x + 2, event.y + offset, true);
		// gc.setForeground(background);
		// gc.setBackground(foreground);
		// }
		// }
		// });

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
				StatsNameColumnLabelProvider.getInstance());

		// Count column
		ViewerHelper.createColumn(viewer, Messages.columnCount, 50,
				StatsCountColumnLabelProvider.getInstance());

		// Size column
		ViewerHelper.createColumn(viewer, Messages.columnSize, 50,
				StatsSizeColumnLabelProvider.getInstance());
	}
}
