package fr.opensagres.mongodb.ide.core.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.mongodb.ide.core.internal.Messages";//$NON-NLS-1$

	public static String errorStopAlreadyStopped;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
