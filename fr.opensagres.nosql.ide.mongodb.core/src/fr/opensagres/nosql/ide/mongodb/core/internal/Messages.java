package fr.opensagres.nosql.ide.mongodb.core.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.nosql.ide.mongodb.core.internal.Messages";//$NON-NLS-1$

	public static String errorStopAlreadyStopped;
	
	public static String Collections_label;
	public static String GridFS_label;
	public static String StoredJavascript_label;
	public static String Users_label;
	public static String Indexes_label;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
