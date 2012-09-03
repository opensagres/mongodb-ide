package fr.opensagres.nosql.ide.mongodb.ui.wizards.server;

import java.util.List;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.mongodb.MongoURI;
import com.mongodb.tools.driver.MongoDriverHelper;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.ServerUI;
import fr.opensagres.nosql.ide.ui.viewers.RuntimeContentProvider;
import fr.opensagres.nosql.ide.ui.viewers.RuntimeLabelProvider;
import fr.opensagres.nosql.ide.ui.wizards.AbstractWizardPage;

public class NewServerWizardPage extends AbstractWizardPage {

	private static final String PAGE_NAME = "NewServerWizardPage";

	private Text mongoURIText;
	private Text nameText;
	private Combo hostCombo;
	private Combo portCombo;
	private ComboViewer runtimeViewer;
	private Text userNameText;
	private Text passwordText;
	private Text databaseNameText;
	private Widget currentWidgetWhichModifyMongoURI;

	protected NewServerWizardPage() {
		super(PAGE_NAME);
		super.setTitle(Messages.NewServerWizardPage_title);
		super.setDescription(Messages.NewServerWizardPage_desc);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_SERVER));
	}

	@Override
	protected Composite createUI(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

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

		// Location Group
		createLocationGroup(container);

		// Authentication Group
		createAuthenticationGroup(container);

		// Runtime
		Label runtimeLabel = new Label(container, SWT.NONE);
		runtimeLabel.setText(Messages.NewServerWizardPage_runtime_label);
		runtimeViewer = new ComboViewer(container, SWT.BORDER | SWT.READ_ONLY);
		runtimeViewer.setLabelProvider(RuntimeLabelProvider.getInstance());
		runtimeViewer.setContentProvider(RuntimeContentProvider.getInstance());
		//runtimeViewer.setInput(Platform.getServerRuntimeManager().getRuntimes());
		runtimeViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		return container;
	}

	private void createLocationGroup(Composite parent) {
		Group container = new Group(parent, SWT.NONE);
		container.setText(Messages.NewServerWizardPage_locationGroup_label);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// MongoURI
		Label mongoURILabel = new Label(container, SWT.NONE);
		mongoURILabel.setText(Messages.NewServerWizardPage_mongoURI_label);
		mongoURIText = new Text(container, SWT.BORDER);
		mongoURIText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateFieldsFromMongoURI();
				validate();
			}
		});
		mongoURIText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Host
		Label hostLabel = new Label(container, SWT.NONE);
		hostLabel.setText(Messages.NewServerWizardPage_host_label);
		hostCombo = new Combo(container, SWT.BORDER);
		hostCombo.setItems(ServerUI.getLocalhosts());
		hostCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMongoURIField(hostCombo);
				validate();
			}
		});
		hostCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Port
		Label portLabel = new Label(container, SWT.NONE);
		portLabel.setText(Messages.NewServerWizardPage_port_label);
		portCombo = new Combo(container, SWT.BORDER);
		portCombo.setItems(ServerUI.getDefaultPorts());
		portCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMongoURIField(portCombo);
				validate();
			}
		});
		portCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createAuthenticationGroup(Composite parent) {
		Group container = new Group(parent, SWT.NONE);
		container
				.setText(Messages.NewServerWizardPage_authenticationGroup_label);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// Username
		Label userNameLabel = new Label(container, SWT.NONE);
		userNameLabel.setText(Messages.NewServerWizardPage_userName_label);
		userNameText = new Text(container, SWT.BORDER);
		userNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMongoURIField(userNameText);
				validate();
			}
		});
		userNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Password
		Label passwordLabel = new Label(container, SWT.NONE);
		passwordLabel.setText(Messages.NewServerWizardPage_password_label);
		passwordText = new Text(container, SWT.BORDER);
		passwordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMongoURIField(passwordText);
				validate();
			}
		});
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Database
		Label databaseNameLabel = new Label(container, SWT.NONE);
		databaseNameLabel
				.setText(Messages.NewServerWizardPage_databaseName_label);
		databaseNameText = new Text(container, SWT.BORDER);
		databaseNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateMongoURIField(databaseNameText);
				validate();
			}
		});
		databaseNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Override
	protected boolean validateFields() {
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
		// Mongo URI validation
		try {
			new MongoURI(mongoURIText.getText());
		} catch (Throwable e) {
			setErrorMessage(NLS.bind(
					Messages.NewServerWizardPage_mongoURI_validation_notValid,
					e.getMessage()));
			return false;
		}
		return true;
	}

	public String getName() {
		return nameText.getText();
	}

	public MongoURI getMongoURI() {
		return MongoDriverHelper.createMongoURI(hostCombo.getText(), getPort(),
				userNameText.getText(), passwordText.getText(),
				databaseNameText.getText());
	}

	public IServerRuntime getRuntime() {
		IStructuredSelection selection = (IStructuredSelection) runtimeViewer
				.getSelection();
		if (selection != null && !selection.isEmpty()) {
			return (IServerRuntime) selection.getFirstElement();
		}
		return null;
	}

	public Integer getPort() {
		if (StringUtils.isNotEmpty(portCombo.getText())) {
			return Integer.parseInt(portCombo.getText());
		}
		return null;
	}

	private void updateMongoURIField(Widget widget) {
		if (currentWidgetWhichModifyMongoURI != null) {
			return;
		}
		try {
			currentWidgetWhichModifyMongoURI = widget;
			mongoURIText.setText(MongoDriverHelper.createStringMongoURI(
					hostCombo.getText(), getPort(), userNameText.getText(),
					passwordText.getText(), databaseNameText.getText()));

		} finally {
			currentWidgetWhichModifyMongoURI = null;
		}
	}

	private void updateFieldsFromMongoURI() {
		try {
			String host = null;
			String port = null;
			String userName = null;
			String password = null;
			String databaseName = null;
			try {
				MongoURI mongoURI = new MongoURI(mongoURIText.getText());
				// Retrieve host and port fields from the MongoURI
				List<String> hosts = mongoURI.getHosts();
				if (hosts != null && hosts.size() > 0) {
					String hostAndPort = hosts.get(0);
					int index = hostAndPort.indexOf(":");
					if (index > 0) {
						host = hostAndPort.substring(0, index);
						port = hostAndPort.substring(index + 1,
								hostAndPort.length());
					} else {

						host = hostAndPort;
						port = null;
					}
				}
				// Retrieve authentification from the MongoURI
				userName = mongoURI.getUsername();
				char[] p = mongoURI.getPassword();
				if (p != null && p.length > 0) {
					password = String.valueOf(p);
				}
				// Retrieve database name from the MongoURI
				databaseName = mongoURI.getDatabase();

			} catch (Throwable e) {

			}
			updateText(hostCombo, host);
			updateText(portCombo, port);
			updateText(userNameText, userName);
			updateText(passwordText, password);
			updateText(databaseNameText, databaseName);
		} finally {

		}
	}

	private void updateText(Text widget, String text) {
		if (!widget.equals(currentWidgetWhichModifyMongoURI)) {
			widget.setText(text != null ? text : "");
		}
	}

	private void updateText(Combo widget, String text) {
		if (!widget.equals(currentWidgetWhichModifyMongoURI)) {
			widget.setText(text != null ? text : "");
		}
	}

	@Override
	protected void onLoad() {
		// Do nothing.
	}
}
