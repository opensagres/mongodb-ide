package fr.opensagres.mongodb.ide.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.mongodb.tools.shell.ShellCommandManager;
import com.mongodb.tools.shell.SysoutShellListener;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.utils.BundleUtils;

public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.opensagres.mongodb.ide.core"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Platform.getMongoRuntimeManager().initialize();
		Platform.getServerManager().initialize();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		Platform.getServerManager().dispose();
		Platform.getMongoInstanceManager().dispose();
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
