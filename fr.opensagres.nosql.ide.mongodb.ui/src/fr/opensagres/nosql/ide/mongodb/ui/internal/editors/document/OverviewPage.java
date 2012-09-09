package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document;

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

import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.Collection;
import fr.opensagres.nosql.ide.mongodb.core.model.Database;
import fr.opensagres.nosql.ide.mongodb.core.model.Document;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.nosql.ide.ui.FormLayoutFactory;
import fr.opensagres.nosql.ide.ui.UIFieldsFactory;
import fr.opensagres.nosql.ide.ui.editors.AbstractToolbarFormPage;

public class OverviewPage extends AbstractToolbarFormPage<DocumentEditor> {

	public static final String ID = "overview";
	private Label serverLabel;
	private Text idText;
	private Label databaseLabel;
	private Label collectionLabel;

	public OverviewPage(DocumentEditor editor) {
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

		initialize();
	}

	protected void createGeneralInfoSection(Composite left, FormToolkit toolkit) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.DocumentEditor_OverviewPage_GeneralInfo_desc);
		section.setText(Messages.DocumentEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// Server name
		Document document = getEditor().getModelObject();
		ICollection collection = document.getParent();
		IDatabase database = collection.getDatabase();
		IServer server = database.getParent();

		serverLabel = UIFieldsFactory.createServerField(sbody, toolkit, server);

		// Database name
		databaseLabel = UIFieldsFactory.createDatabaseField(sbody, toolkit,
				database);

		// Collection name
		collectionLabel = UIFieldsFactory.createCollectionField(sbody, toolkit,
				collection);

		// Document id
		toolkit.createLabel(sbody, Messages.document_label);
		idText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData gd_firstNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_firstNameText.widthHint = 150;
		idText.setLayoutData(gd_firstNameText);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

	}

	private void initialize() {

		Document document = getEditor().getModelObject();
		ICollection collection = document.getParent();
		IDatabase database = collection.getDatabase();
		IServer server = database.getParent();
		serverLabel.setText(server.getName());
		databaseLabel.setText(database.getName());
		collectionLabel.setText(collection.getName());
		idText.setText(document.getName());
	}

}
