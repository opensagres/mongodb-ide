package fr.opensagres.nosql.ide.mongodb.core.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.opensagres.nosql.ide.mongodb.core"; //$NON-NLS-1$

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
		Platform.getShellCommandManagerRegistry().add(
				MongoShellCommandManager.getInstance());
	}

	public void stop(BundleContext context) throws Exception {
		Platform.getShellCommandManagerRegistry().remove(
				MongoShellCommandManager.getInstance());
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
