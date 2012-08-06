package fr.opensagres.mongodb.ide.ui.editors.collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.FormLayoutFactory;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.editors.UIFieldsFactory;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.singlesourcing.SingleSourcingUtils;

public class OverviewPage extends AbstractToolbarFormPage {

	public static final String ID = "overview";
	private Label serverLabel;
	private Text nameText;
	private Label databaseLabel;

	public OverviewPage(CollectionEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
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

		// createTimeoutSection(right, toolkit);

		// "org.eclipse.wst.server.editor.overview.right");

		initialize();
	}

	protected void createGeneralInfoSection(Composite left, FormToolkit toolkit) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.databaseEditorOverviewGeneralDescription);
		section.setText(Messages.databaseEditorOverviewGeneralSection);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// Server name
		Collection collection = ((CollectionEditor) getEditor())
				.getModelObject();
		Database database = collection.getDatabase();
		Server server = database.getParent();
		serverLabel = UIFieldsFactory.createServerField(sbody, toolkit, server);

		// Database name
		databaseLabel = UIFieldsFactory.createDatabaseField(sbody, toolkit,
				database);

		// Collection name
		toolkit.createLabel(sbody, Messages.collection_label);
		nameText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData gd_firstNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_firstNameText.widthHint = 150;
		nameText.setLayoutData(gd_firstNameText);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

	}

	private void initialize() {

		Collection collection = ((CollectionEditor) getEditor())
				.getModelObject();
		Database database = collection.getDatabase();
		Server server = database.getParent();
		serverLabel.setText(server.getName());
		databaseLabel.setText(database.getName());
		nameText.setText(collection.getName());
	}

}
