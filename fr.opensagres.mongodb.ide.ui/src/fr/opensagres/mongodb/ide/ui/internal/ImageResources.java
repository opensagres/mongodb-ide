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
	public static final String PATH_CLCL_16 = ICONS_PATH + "clcl16/"; //$NON-NLS-1$
	public static final String PATH_DLCL_16 = ICONS_PATH + "dlcl16/"; //$NON-NLS-1$
	public static final String PATH_ELCL_16 = ICONS_PATH + "elcl16/"; //$NON-NLS-1$
	public static final String PATH_WIZBAN = ICONS_PATH + "wizban/";//$NON-NLS-1$
	public static final String PATH_CHECKBOXES = ICONS_PATH + "checboxes/"; //$NON-NLS-1$
	
	public static final String IMG_SERVER_16 = "server_16";
	public static final String IMG_SERVER_STARTED_16 = "server_started_16";
	public static final String IMG_SERVER_STOPPED_16 = "server_stopped_16";
	public static final String IMG_SERVER_NEW_16 = "server_new_16";
	public static final String IMG_REFRESH_16 = "refresh_16";
	public static final String IMG_DELETE_16 = "delete_16";
	public static final String IMG_DATABASE_16 = "database_16";
	public static final String IMG_COLLECTION_16 = "collection_16";
	public static final String IMG_DOCUMENT_16 = "document_16";
	public static final String IMG_ERROR_16 = "error_16";
	public static final String IMG_CATEGORY_16 = "category_16";
	public static final String IMG_USERS_16 = "users_16";
	public static final String IMG_INDEX_16 = "index_16";
	public static final String IMG_TH_HORIZONTAL_16 = "th_horizontal_16";
	public static final String IMG_TH_VERTICAL_16 = "th_vertical_16";
	
	public static final String IMG_ELCL_START = "start_elcl_16";
	public static final String IMG_CLCL_START = "start_clcl_16";
	public static final String IMG_DLCL_START = "start_dlcl_16";
	public static final String IMG_ELCL_STOP = "stop_elcl_16";
	public static final String IMG_CLCL_STOP = "stop_clcl_16";
	public static final String IMG_DLCL_STOP = "stop_dlcl_16";
	
	public static final String IMG_WIZBAN_NEW_SERVER = "newServerWiz";

	public static void initialize(ImageRegistry imageRegistry) {
		// registerImage(imageRegistry, Activator.PLUGIN_ID, MISSING);
		registerImage(imageRegistry, IMG_SERVER_16, PATH_OBJ_16 + "server.gif");
		registerImage(imageRegistry, IMG_SERVER_STARTED_16, PATH_OBJ_16
				+ "server_started.gif");
		registerImage(imageRegistry, IMG_SERVER_STOPPED_16, PATH_OBJ_16
				+ "server_stopped.gif");
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
		registerImage(imageRegistry, IMG_CATEGORY_16, PATH_OBJ_16 + "category.gif");
		registerImage(imageRegistry, IMG_USERS_16, PATH_OBJ_16 + "users.png");
		registerImage(imageRegistry, IMG_INDEX_16, PATH_OBJ_16 + "index.png");
		registerImage(imageRegistry, IMG_TH_HORIZONTAL_16, PATH_OBJ_16 + "th_horizontal.gif");
		registerImage(imageRegistry, IMG_TH_VERTICAL_16, PATH_OBJ_16 + "th_vertical.gif");
		
		registerImage(imageRegistry, IMG_ELCL_START, PATH_ELCL_16
				+ "launch_run.gif");
		registerImage(imageRegistry, IMG_CLCL_START, PATH_CLCL_16
				+ "launch_run.gif");
		registerImage(imageRegistry, IMG_DLCL_START, PATH_DLCL_16
				+ "launch_run.gif");
		registerImage(imageRegistry, IMG_ELCL_STOP, PATH_ELCL_16
				+ "launch_stop.gif");
		registerImage(imageRegistry, IMG_CLCL_STOP, PATH_CLCL_16
				+ "launch_stop.gif");
		registerImage(imageRegistry, IMG_DLCL_STOP, PATH_DLCL_16
				+ "launch_stop.gif");
		
		registerImage(imageRegistry, IMG_WIZBAN_NEW_SERVER, PATH_WIZBAN
				+ "new_server_wiz.png");		
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
