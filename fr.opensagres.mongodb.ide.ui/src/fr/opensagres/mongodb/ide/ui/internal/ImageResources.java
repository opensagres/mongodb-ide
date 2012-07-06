package fr.opensagres.mongodb.ide.ui.internal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

public class ImageResources {

	public final static String ICONS_PATH = "icons/"; //$NON-NLS-1$

	/**
	 * Set of predefined Image Descriptors.
	 */
	public static final String PATH_OBJ_16 = ICONS_PATH + "obj16/"; //$NON-NLS-1$

	public static final String IMG_SERVER_16 = "server_16";
	public static final String IMG_SERVER_NEW_16 = "server_new_16";
	public static final String IMG_REFRESH_16 = "refresh_16";
	public static final String IMG_DELETE_16 = "delete_16";
	public static final String IMG_DATABASE_16 = "database_16";
	public static final String IMG_COLLECTION_16 = "collection_16";
	public static final String IMG_DOCUMENT_16 = "document_16";
	public static final String IMG_ERROR_16 = "error_16";

	public static void initialize(ImageRegistry imageRegistry) {
		// registerImage(imageRegistry, Activator.PLUGIN_ID, MISSING);
		registerImage(imageRegistry, IMG_SERVER_16, PATH_OBJ_16 + "server.png");
		registerImage(imageRegistry, IMG_SERVER_NEW_16, PATH_OBJ_16
				+ "server-new.png");
		registerImage(imageRegistry, IMG_REFRESH_16, PATH_OBJ_16
				+ "refresh.gif");
		registerImage(imageRegistry, IMG_DELETE_16, PATH_OBJ_16 + "delete.gif");
		registerImage(imageRegistry, IMG_DATABASE_16, PATH_OBJ_16
				+ "database.png");
		registerImage(imageRegistry, IMG_COLLECTION_16, PATH_OBJ_16
				+ "collection.png");
		registerImage(imageRegistry, IMG_DOCUMENT_16, PATH_OBJ_16
				+ "document.gif");
		registerImage(imageRegistry, IMG_ERROR_16, PATH_OBJ_16 + "error.gif");
	}

	private static void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path(fileName);
			Bundle bundle = Activator.getDefault().getBundle();
			URL url = FileLocator.find(bundle, path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		ImageDescriptor img = imageRegistry.getDescriptor(key);
		if (img == null) {
			registerImage(imageRegistry, Activator.PLUGIN_ID, key);
			img = imageRegistry.getDescriptor(key);
		}
		return img;
	}

	public static Image getImage(String key) {
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		Image img = imageRegistry.get(key);
		if (img == null) {
			registerImage(imageRegistry, Activator.PLUGIN_ID, key);
			img = imageRegistry.get(key);
		}
		return img;
	}

}
