package fr.opensagres.mongodb.ide.ui.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.mongodb.ide.ui.internal.Messages";//$NON-NLS-1$

	// ****************** NewServerWizardPage ******************

	public static String NewServerWizardPage_title;
	public static String NewServerWizardPage_name_label;
	public static String NewServerWizardPage_host_label;
	public static String NewServerWizardPage_port_label;
	public static String NewServerWizardPage_name_validation_required;
	public static String NewServerWizardPage_host_validation_required;
	public static String NewServerWizardPage_port_validation_int;
	public static String NewServerWizardPage_runtime_label;
	
	// ****************** NewServerAction ******************
	public static String NewServerAction_text;
	public static String NewServerAction_toolTipText;

	// ****************** RefreshAction ******************
	public static String RefreshAction_text;
	public static String RefreshAction_toolTipText;

	// ****************** DeleteAction ******************
	public static String DeleteAction_text;
	public static String DeleteAction_toolTipText;

	public static String actionNew;
	public static String actionStart;
	public static String actionStartToolTip;	
	public static String actionStop;
	public static String actionStopToolTip;
	public static String actionRestart;	
	public static String actionRestartToolTip;
	public static String actionUnlock;
	public static String actionUnlockToolTip;
	public static String actionOpen;
	
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
