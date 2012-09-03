package fr.opensagres.nosql.ide.ui.internal.extensions;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.ui.extensions.IServerImage;

public class ServerImage implements IServerImage {

	private final IServerType type;
	private final Image icon;

	public ServerImage(IServerType type, Image icon) {
		this.type = type;
		this.icon = icon;
	}

	public IServerType getType() {
		return type;
	}

	public Image getIcon() {
		return icon;
	}
}
