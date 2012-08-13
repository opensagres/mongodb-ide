package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.viewers.ServerContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.ServerLabelProvider;

public class SelectServerWizardPage extends WizardPage {

	private static final String PAGE_NAME = "SelectServerWizardPage";
	private ComboViewer serverViewer;

	public SelectServerWizardPage() {
		super(PAGE_NAME);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// Display list of server.
		Label serverLabel = new Label(container, SWT.NONE);
		serverLabel.setText(Messages.server_label);
		serverViewer = new ComboViewer(container, SWT.BORDER | SWT.READ_ONLY);
		serverViewer.setLabelProvider(ServerLabelProvider.getInstance());
		serverViewer.setContentProvider(ServerContentProvider.getInstance());
		serverViewer.setInput(Platform.getServerManager().getServers());
		serverViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		setControl(container);
		validate();
	}

	private void validate() {

	}

	public Server getServer() {
		return (Server) ((IStructuredSelection) serverViewer.getSelection())
				.getFirstElement();
	}

}
