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
	public static String InstalledRuntimesBlock_nameColumn;
	public static String InstalledRuntimesBlock_installDirColumn;
	
	// ****************** AddRuntimeDialog ******************
	public static String AddRuntimeDialog_title;
	public static String EditRuntimeDialog_title;
	public static String AddRuntimeDialog_desc;
	public static String AddRuntimeDialog_runtimeName;
	public static String AddRuntimeDialog_installDir;
	public static String AddRuntimeDialog_selectInstallDir;

	// MongodLaunchConfigurationTab
	public static String jobStarting;
	public static String jobRestarting;
	public static String jobStopping;
	public static String serverLaunchDescription;
	public static String serverLaunchServer;
	public static String serverLaunchHost;
	public static String serverLaunchRuntime;

	// Server Error
	public static String errorServerAlreadyRunning;
	public static String errorInvalidServer;
	public static String errorNoServerSelected;
	public static String errorStartFailed;
	public static String errorStartTimeout;
	public static String errorStopFailed;
	// Runtime error
	public static String errorRuntimeNameRequired;
	public static String errorInstallDirRequired;
	public static String errorInstallDir_baseDirNotExists;
	public static String errorInstallDir_baseDirNotDir;
	public static String errorInstallDir_binDirNotExists;
	public static String errorInstallDir_processFileNotExists;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
