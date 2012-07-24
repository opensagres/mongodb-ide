package fr.opensagres.mongodb.ide.launching.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.mongodb.ide.launching.internal.Messages";//$NON-NLS-1$

	public static String browseButton;
	public static String addButton;
	public static String editButton;
	public static String removeButton;

	// ****************** ServerLaunchConfigurationTab ******************

	public static String ServerLaunchConfigurationTab_name;

	// ****************** MongoRuntimePreferencePage ******************
	public static String MongoRuntimePreferencePage_title;
	public static String MongoRuntimePreferencePage_desc;
	public static String InstalledRuntimesBlock_desc;
		
	public static String InstalledRuntimesBlock_2;

	public static String InstalledRuntimesBlock_1;



	// ****************** AddRuntimeDialog ******************
	public static String AddRuntimeDialog_title;
	public static String AddRuntimeDialog_desc;
	public static String AddRuntimeDialog_runtimeName;
	public static String AddRuntimeDialog_installDir;
	public static String AddRuntimeDialog_selectInstallDir;
	
	// MongodLaunchConfigurationTab
	public static String jobStarting;
	public static String jobStopping;
	public static String serverLaunchDescription;
	public static String serverLaunchServer;
	public static String serverLaunchHost;
	public static String serverLaunchRuntime;
	
	// Error
	public static String errorServerAlreadyRunning;
	public static String errorInvalidServer;
	public static String errorNoServerSelected;
	public static String errorStartFailed;
	public static String errorStartTimeout;
	public static String errorStopFailed;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
