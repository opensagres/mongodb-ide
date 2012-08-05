package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mongodb.DBObject;
import com.mongodb.tools.driver.DBObjectHelper;

import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class DBUserLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	private static DBUserLabelProvider instance;

	public static DBUserLabelProvider getInstance() {
		synchronized (DBUserLabelProvider.class) {
			if (instance == null) {
				instance = new DBUserLabelProvider();
			}
			return instance;
		}
	}

	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ImageResources.getImage(ImageResources.IMG_USERS_16);
		case 1:
			if (DBObjectHelper.isReadonlyOfUser(((DBObject) element))) {
				return ImageResources
						.getImage(ImageResources.IMG_ENABLED_CHECKED);
			}
			return ImageResources
					.getImage(ImageResources.IMG_ENABLED_UNCHECKED);
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return DBObjectHelper.getUsernameOfUser(((DBObject) element));
		}
		return null;
	}
}
