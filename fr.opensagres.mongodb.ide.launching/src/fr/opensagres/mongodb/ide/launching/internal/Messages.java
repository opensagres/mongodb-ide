package fr.opensagres.mongodb.ide.launching.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.mongodb.ide.launching.internal.Messages";//$NON-NLS-1$

	public static String Button_browse;
	
	// ****************** ServerLaunchConfigurationTab ******************

	public static String ServerLaunchConfigurationTab_name;

	// ****************** MongoRuntimePreferencePage ******************
	public static String MongoRuntimePreferencePage_title;
	public static String MongoRuntimePreferencePage_desc;

	public static String InstalledRuntimesBlock_addButton;
	public static String InstalledRuntimesBlock_editButton;
	public static String InstalledRuntimesBlock_removeButton;

	public static String InstalledRuntimesBlock_2;

	public static String InstalledRuntimesBlock_1;

	public static String InstalledRuntimesBlock_0;

	// ****************** AddRuntimeDialog ******************
	public static String AddRuntimeDialog_title;
	public static String AddRuntimeDialog_desc;
	public static String AddRuntimeDialog_runtimeName;
	public static String AddRuntimeDialog_installDir;
	public static String AddRuntimeDialog_selectInstallDir;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
