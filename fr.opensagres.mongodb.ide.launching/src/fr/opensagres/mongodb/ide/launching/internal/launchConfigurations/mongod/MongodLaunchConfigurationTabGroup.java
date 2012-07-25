package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class MongodLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[3];
		tabs[0] = new MongodLaunchConfigurationTab();
		tabs[0].setLaunchConfigurationDialog(dialog);
		tabs[1] = new EnvironmentTab();
		tabs[1].setLaunchConfigurationDialog(dialog);
		tabs[2] = new CommonTab();
		tabs[2].setLaunchConfigurationDialog(dialog);
		setTabs(tabs);
	}

}
