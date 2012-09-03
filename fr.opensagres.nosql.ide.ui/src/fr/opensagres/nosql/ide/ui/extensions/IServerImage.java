package fr.opensagres.nosql.ide.ui.extensions;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.extensions.IServerType;

public interface IServerImage {

	IServerType getType();

	Image getIcon();
}
