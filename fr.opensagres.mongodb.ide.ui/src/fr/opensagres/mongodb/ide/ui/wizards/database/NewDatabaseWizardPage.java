package fr.opensagres.mongodb.ide.ui.wizards.database;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;

public class NewDatabaseWizardPage extends WizardPage {

	private static final String PAGE_NAME = "NewDatabaseWizardPage";

	private Text nameText;

	public NewDatabaseWizardPage() {
		super(PAGE_NAME);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		Server server = ((AbstractSelectNodeWizard) getWizard()).getServer();
		// Server Name
		Label serverLabel = new Label(container, SWT.NONE);
		serverLabel.setText(Messages.server_label);
		serverLabel = new Label(container, SWT.NONE);
		serverLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverLabel.setText(server.getLabel());

		// Database Name
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(Messages.NewServerWizardPage_name_label);
		nameText = new Text(container, SWT.BORDER);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		setControl(container);
		validate();
	}

	private void validate() {

	}

	public String getDatabaseName() {
		return nameText.getText();
	}
}
