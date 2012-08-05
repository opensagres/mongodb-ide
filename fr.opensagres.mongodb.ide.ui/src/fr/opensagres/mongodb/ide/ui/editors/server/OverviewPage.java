package fr.opensagres.mongodb.ide.ui.editors.server;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.ui.FormLayoutFactory;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.singlesourcing.SingleSourcingUtils;

public class OverviewPage extends AbstractToolbarFormPage {

	public static final String ID = "overview";
	private Text hostText;
	private Text serverNameText;
	private Text portText;
	private Text databaseText;

	public OverviewPage(ServerEditor editor) {
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

		createTimeoutSection(right, toolkit);

		// insertSections(rightColumnComp,
		// "org.eclipse.wst.server.editor.overview.right");

		// initialize();
	}

	protected void createGeneralInfoSection(Composite left, FormToolkit toolkit) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.serverEditorOverviewGeneralDescription);
		section.setText(Messages.serverEditorOverviewGeneralSection);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// Server name
		toolkit.createLabel(sbody, Messages.serverNameLabel);
		serverNameText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		serverNameText.setLayoutData(gridData);

		// Host
		toolkit.createLabel(sbody, Messages.hostLabel);
		hostText = toolkit.createText(sbody, "", SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		hostText.setLayoutData(gridData);

		// Port
		toolkit.createLabel(sbody, Messages.portLabel);
		portText = toolkit.createText(sbody, "", SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		portText.setLayoutData(gridData);

		// Database
		toolkit.createLabel(sbody, Messages.databaseLabel);
		databaseText = toolkit.createText(sbody, "", SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		databaseText.setLayoutData(gridData);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

		initialize();

	}

	private void initialize() {
		Server server = ((ServerEditor) getEditor()).getModelObject();
		serverNameText.setText(server.getName());
		hostText.setText(server.getHost());
		Integer port = server.getPort();
		if (port != null) {
			hostText.setText(String.valueOf(port));
		}
		String database = server.getDatabaseName();
		if (StringUtils.isNotEmpty(database)) {
			databaseText.setText(database);
		}
	}

	protected void createTimeoutSection(Composite rightColumnComp,
			FormToolkit toolkit) {

	}

}
