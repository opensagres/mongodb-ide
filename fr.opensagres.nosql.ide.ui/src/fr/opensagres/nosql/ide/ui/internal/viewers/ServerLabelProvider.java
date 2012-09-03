package fr.opensagres.nosql.ide.ui.internal.viewers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IModelIdentity;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.ui.PlatformUI;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;

public class ServerLabelProvider extends LabelProvider {

	private static ServerLabelProvider instance;

	public static ServerLabelProvider getInstance() {
		synchronized (ServerLabelProvider.class) {
			if (instance == null) {
				instance = new ServerLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IModelIdentity) {
			return ((IModelIdentity) element).getLabel();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IServerType) {
			IServerType serverType = ((IServerType) element);
			return PlatformUI.getServerImageRegistry().getImage(serverType);
		}
		if (element instanceof ITreeSimpleNode) {
			int type = ((ITreeSimpleNode) element).getType();
			switch (type) {
			case NodeTypeConstants.Server:
				IServer server = (IServer) element;
				switch (server.getServerState()) {
				case Started:
					return ImageResources
							.getImage(ImageResources.IMG_SERVER_STARTED_16);
				case Stopped:
					return ImageResources
							.getImage(ImageResources.IMG_SERVER_STOPPED_16);
				default:
					return ImageResources.getImage(ImageResources.IMG_SERVER_16);
				}
			case NodeTypeConstants.Database:
				return ImageResources.getImage(ImageResources.IMG_DATABASE_16);
			case NodeTypeConstants.Collection:
				return ImageResources.getImage(ImageResources.IMG_COLLECTION_16);
			case NodeTypeConstants.Document:
				return ImageResources.getImage(ImageResources.IMG_DOCUMENT_16);
			case NodeTypeConstants.Error:
				return ImageResources.getImage(ImageResources.IMG_ERROR_16);
			}
		}
		return super.getImage(element);
	}
}
