package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.launching.internal.ImageResources;
import fr.opensagres.mongodb.ide.launching.internal.LaunchHelper;
import fr.opensagres.mongodb.ide.launching.internal.Messages;

public class MongodLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab {

	// flag to be used to decide whether to enable combo in launch config dialog
	// after the user requests a launch, they cannot change it
	private static final String READ_ONLY = "read-only";

	private Combo serverCombo;

	private Label runtimeLabel;
	private Label hostname;

	private Server server;

	// list of servers that are in combo
	private List<Server> servers;

	private ILaunchConfigurationWorkingCopy wc;

	/**
	 * Create a new server launch configuration tab.
	 */
	public MongodLaunchConfigurationTab() {
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 2;
		composite.setLayout(layout);

		// Description label
		Label label = new Label(composite, SWT.WRAP);
		label.setText(Messages.serverLaunchDescription);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = false;
		data.widthHint = 300;
		label.setLayoutData(data);

		// Server combo
		label = new Label(composite, SWT.NONE);
		label.setText(Messages.serverLaunchServer);
		serverCombo = new Combo(composite, SWT.SINGLE | SWT.BORDER
				| SWT.READ_ONLY);
		serverCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				handleServerSelection();
			}
		});
		// PlatformUI
		// .getWorkbench()
		// .getHelpSystem()
		// .setHelp(serverCombo,
		// ContextIds.LAUNCH_CONFIGURATION_SERVER_COMBO);

		// Runtime label
		label = new Label(composite, SWT.NONE);
		label.setText(Messages.serverLaunchRuntime);
		runtimeLabel = new Label(composite, SWT.NONE);
		runtimeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Host label
		label = new Label(composite, SWT.NONE);
		label.setText(Messages.serverLaunchHost);
		hostname = new Label(composite, SWT.NONE);
		hostname.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// initialize
		List<Server> servers2 = Platform.getServerManager().getServers();
		servers = new ArrayList<Server>();
		if (servers2 != null) {
			for (Server server2 : servers2) {
				if (server2.getRuntime() != null) {
					serverCombo.add(server2.getName());
					servers.add(server2);
				}
			}
		}

		// select first item in list
		if (serverCombo.getItemCount() > 0)
			serverCombo.select(0);

		handleServerSelection();

		serverCombo.forceFocus();

		Dialog.applyDialogFont(composite);
		setControl(composite);
	}

	/**
	 * Called when a server is selected. This method should not be called
	 * directly.
	 */
	protected void handleServerSelection() {
		if (servers.isEmpty())
			server = null;
		else
			server = servers.get(serverCombo.getSelectionIndex());
		MongoRuntime runtime = null;
		if (server != null) {
			runtime = server.getRuntime();
			hostname.setText(server.getHost());
		} else
			hostname.setText("");

		if (runtime != null) {
			runtimeLabel.setText(runtime.getName());
		} else {
			runtimeLabel.setText("");
		}
		//
		// // check if "runtime" property is true or false
		// if (runtime != null && server != null && server.getServerType() !=
		// null
		// && server.getServerType().hasRuntime())
		// runtimeLabel.setText(runtime.getName());
		// else
		// runtimeLabel.setText("");
		//
		// try {
		// if (wc != null)
		// ((Server) server).setupLaunchConfiguration(wc,
		// new NullProgressMonitor());
		// } catch (Exception e) {
		// // ignore
		// }
		//
		if (server == null)
			setErrorMessage(Messages.errorNoServerSelected);
		else if (server.getServerState() != ServerState.Stopped)
			setErrorMessage(Messages.errorServerAlreadyRunning);
		else
			setErrorMessage(null);

		updateLaunchConfigurationDialog();
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		setErrorMessage(null);
		if (serverCombo != null) {
			serverCombo.setEnabled(true);
			if (serverCombo.getItemCount() > 0)
				serverCombo.select(0);
		}

		if (servers != null) {
			server = servers.get(serverCombo.getSelectionIndex());
			// if (server != null)
			// ((Server) server).setupLaunchConfiguration(configuration, null);
		}
		wc = configuration;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		serverCombo.setEnabled(true);
		// remove error message that other instances may have set
		setErrorMessage(null);

		try {
			// bug 137822 - set the ILaunchConfigurationWorkingCopy wc variable
			// before calling the method handleServerSelection()
			// if (configuration instanceof ILaunchConfigurationWorkingCopy)
			// wc = (ILaunchConfigurationWorkingCopy)configuration;

			String serverId = configuration.getAttribute(
					LaunchHelper.ATTR_SERVER_ID, "");
			if (serverId != null && !serverId.equals("")) {
				server = Platform.getServerManager().findServer(serverId);

				if (server == null) { // server no longer exists
					setErrorMessage(Messages.errorInvalidServer);
					// serverCombo.clearSelection(); // appears to be
					// broken...doesn't work with read only?
					serverCombo.setEnabled(false);
					return;
				}

				serverCombo.setText(server.getName());
				if (server.getServerState() != ServerState.Stopped)
					setErrorMessage(Messages.errorServerAlreadyRunning);
			} else {
				if (serverCombo.getItemCount() > 0)
					serverCombo.select(0);
			}
			handleServerSelection();

			// flag should only be set if launch has been attempted on the
			// config
			if (configuration.getAttribute(READ_ONLY, false))
				serverCombo.setEnabled(false);
		} catch (CoreException e) {
			// ignore
		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (server != null)
			configuration.setAttribute(LaunchHelper.ATTR_SERVER_ID,
					server.getId());
		else
			configuration.setAttribute(LaunchHelper.ATTR_SERVER_ID,
					(String) null);
		wc = configuration;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		try {
			String serverId = launchConfig.getAttribute(
					LaunchHelper.ATTR_SERVER_ID, "");
			if (StringUtils.isNotEmpty(serverId)) {
				Server server2 = Platform.getServerManager().findServer(
						serverId);
				if (server2 == null)
					return false;
				if (server2.getServerState() == ServerState.Stopped)
					return true;
			}
		} catch (CoreException e) {
			// ignore
		}
		return false;
	}

	public String getName() {
		return Messages.ServerLaunchConfigurationTab_name;
	}

	@Override
	public Image getImage() {
		return ImageResources.getImage(ImageResources.IMG_MONGO_16);
	}
}
