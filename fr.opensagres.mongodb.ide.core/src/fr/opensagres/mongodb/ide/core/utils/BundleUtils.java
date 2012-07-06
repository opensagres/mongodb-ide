package fr.opensagres.mongodb.ide.core.utils;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class BundleUtils {

	public static void startBundle(String symbolicName) throws BundleException {
		Bundle bundle = Platform.getBundle(symbolicName);
		if (bundle != null
				&& (bundle.getState() != Bundle.STARTING && bundle.getState() != Bundle.ACTIVE)) {
			bundle.start();
		}
	}
}
