package fr.opensagres.nosql.ide.ui.extensions;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.extensions.IRegistry;
import fr.opensagres.nosql.ide.core.extensions.IServerType;

public interface IServerImageRegistry extends IRegistry {

	/**
	 * Return the {@link IServerImage} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	IServerImage getServerImage(IServerType serverType);

	/**
	 * Return the {@link IServerImage} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	Image getImage(IServerType serverType);

	/**
	 * Return the list of the {@link IServerImage}.
	 * 
	 * @return
	 */
	Collection<IServerImage> getImages();
}
