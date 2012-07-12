package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.viewers.RuntimeContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.RuntimeLabelProvider;

public class NewServerWizardPage extends WizardPage {

	private Text nameText;
	private Combo hostCombo;
	private Combo portCombo;
	private ComboViewer runtimeViewer;

	protected NewServerWizardPage() {
		super("ee");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		super.setTitle(Messages.NewServerWizardPage_title);

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// Name
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(Messages.NewServerWizardPage_name_label);
		nameText = new Text(container, SWT.BORDER);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Host
		Label hostLabel = new Label(container, SWT.NONE);
		hostLabel.setText(Messages.NewServerWizardPage_host_label);
		hostCombo = new Combo(container, SWT.BORDER);
		hostCombo.add("localhost");
		hostCombo.add("127.0.0.1");
		hostCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		hostCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Port
		Label portLabel = new Label(container, SWT.NONE);
		portLabel.setText(Messages.NewServerWizardPage_port_label);
		portCombo = new Combo(container, SWT.BORDER);
		portCombo.add("27017");
		portCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		portCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Runtime
		Label runtimeLabel = new Label(container, SWT.NONE);
		runtimeLabel.setText(Messages.NewServerWizardPage_runtime_label);
		runtimeViewer = new ComboViewer(container, SWT.BORDER | SWT.READ_ONLY);
		runtimeViewer.setLabelProvider(RuntimeLabelProvider.getInstance());
		runtimeViewer.setContentProvider(RuntimeContentProvider.getInstance());
		runtimeViewer.setInput(Platform.getMongoRuntimeManager().getRuntimes());
		runtimeViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		setControl(container);
		validate();
	}

	private void validate() {
		super.setPageComplete(validateFields());
		super.getContainer().updateButtons();
	}

	private boolean validateFields() {
		// Name validation
		if (StringUtils.isEmpty(nameText.getText())) {
			setErrorMessage(Messages.NewServerWizardPage_name_validation_required);
			return false;
		}
		// Host validation
		if (StringUtils.isEmpty(hostCombo.getText())) {
			setErrorMessage(Messages.NewServerWizardPage_host_validation_required);
			return false;
		}
		// Port validation
		if (StringUtils.isNotEmpty(portCombo.getText())) {
			try {
				Integer.parseInt(portCombo.getText());
			} catch (Throwable e) {
				setErrorMessage(Messages.NewServerWizardPage_port_validation_int);
				return false;
			}
		}
		setErrorMessage(null);
		return true;
	}

	public String getName() {
		return nameText.getText();
	}

	public String getHost() {
		return hostCombo.getText();
	}

	public MongoRuntime getRuntime() {
		IStructuredSelection selection = (IStructuredSelection) runtimeViewer
				.getSelection();
		if (selection != null && !selection.isEmpty()) {
			return (MongoRuntime) selection.getFirstElement();
		}
		return null;
	}

	public Integer getPort() {
		if (StringUtils.isNotEmpty(portCombo.getText())) {
			return Integer.parseInt(portCombo.getText());
		}
		return null;
	}
}
