package fr.opensagres.nosql.ide.ui.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.opensagres.nosql.ide.core.extensions.AbstractRegistry;

public abstract class AbstractUIRegistry extends AbstractRegistry {

	public static final String ICON_ATTR = "icon";

	protected Image getIconImage(IConfigurationElement cfig) {
		ImageDescriptor imageDescriptor = getIconImageDescriptor(cfig);
		if (imageDescriptor != null) {
			return JFaceResources.getResources().createImageWithDefault(
					imageDescriptor);

		}
		return null;
	}

	protected ImageDescriptor getIconImageDescriptor(IConfigurationElement cfig) {
		String strIcon = cfig.getAttribute(ICON_ATTR);//$NON-NLS-1$
		if (strIcon != null) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					cfig.getNamespaceIdentifier(), strIcon);
		}
		return null;
	}
}
