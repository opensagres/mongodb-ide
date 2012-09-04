package fr.opensagres.nosql.ide.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import fr.opensagres.nosql.ide.core.Platform;

public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.opensagres.nosql.ide.core"; //$NON-NLS-1$

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
		Platform.getServerFactoryRegistry().initialize();
		Platform.getServerRunnerRegistry().initialize();
		Platform.getServerRuntimeManager().initialize();
		Platform.getServerTypeRegistry().initialize();
		Platform.getServerManager().initialize();
	}

	public void stop(BundleContext context) throws Exception {
		Platform.getServerFactoryRegistry().destroy();
		Platform.getServerRunnerRegistry().destroy();
		Platform.getServerRuntimeManager().dispose();
		Platform.getServerTypeRegistry().destroy();
		Platform.getServerManager().dispose();
		plugin = null;
		super.stop(context);
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
