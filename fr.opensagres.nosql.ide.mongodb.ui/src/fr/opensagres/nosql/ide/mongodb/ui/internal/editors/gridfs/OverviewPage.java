package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.gridfs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.core.model.GridFSBucket;
import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.nosql.ide.ui.FormLayoutFactory;
import fr.opensagres.nosql.ide.ui.UIFieldsFactory;
import fr.opensagres.nosql.ide.ui.editors.AbstractToolbarFormPage;

public class OverviewPage extends AbstractToolbarFormPage<GridFSEditor> {

	public static final String ID = "overview";
	private Label serverLabel;
	private Text gridFSText;
	private Label databaseLabel;

	public OverviewPage(GridFSEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_GRIDFS_16);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

		Composite left = toolkit.createComposite(body);
		left.setLayout(FormLayoutFactory
				.createFormPaneTableWrapLayout(false, 1));
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// General info section
		createGeneralInfoSection(left, toolkit);

		// right column
		Composite right = toolkit.createComposite(body);
		right.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false,
				1));
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		// Content section
		// createContentSection(toolkit, right);

		// createTimeoutSection(right, toolkit);

		// "org.eclipse.wst.server.editor.overview.right");

		initialize();
	}

	protected void createGeneralInfoSection(Composite left, FormToolkit toolkit) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.GridFSEditor_OverviewPage_GeneralInfo_desc);
		section.setText(Messages.GridFSEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// Server name
		GridFSBucket gridFS = ((GridFSEditor) getEditor()).getModelObject();
		Database database = gridFS.getParent().getParent();
		IServer server = database.getParent();
		serverLabel = UIFieldsFactory.createServerField(sbody, toolkit, server);

		// Database name
		databaseLabel = UIFieldsFactory.createDatabaseField(sbody, toolkit,
				database);

		// Bucket GridFS name
		toolkit.createLabel(sbody, Messages.bucket_label);
		gridFSText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData gd_firstNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_firstNameText.widthHint = 150;
		gridFSText.setLayoutData(gd_firstNameText);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

	}

	private void initialize() {

		GridFSBucket gridFS = ((GridFSEditor) getEditor()).getModelObject();
		Database database = gridFS.getParent().getParent();
		IServer server = database.getParent();
		serverLabel.setText(server.getName());
		databaseLabel.setText(database.getName());
		gridFSText.setText(gridFS.getName());
	}

}
