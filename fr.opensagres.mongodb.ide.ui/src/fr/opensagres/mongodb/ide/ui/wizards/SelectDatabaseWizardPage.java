package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class SelectDatabaseWizardPage extends WizardPage {

	private static final String PAGE_NAME = "SelectDatabaseWizardPage";

	public SelectDatabaseWizardPage() {
		super(PAGE_NAME);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		setControl(container);
		validate();
	}

	private void validate() {

	}


}
