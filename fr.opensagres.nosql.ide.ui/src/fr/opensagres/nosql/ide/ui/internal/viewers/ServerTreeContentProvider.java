package fr.opensagres.nosql.ide.ui.internal.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.ITreeContainerNode;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeContainerNode;

public class ServerTreeContentProvider extends ArrayContentProvider implements
		ITreeContentProvider {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	private static ServerTreeContentProvider instance;

	public static ServerTreeContentProvider getInstance() {
		synchronized (ServerTreeContentProvider.class) {
			if (instance == null) {
				instance = new ServerTreeContentProvider();
			}
			return instance;
		}
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IServerType) {
			return Platform.getServerManager()
					.getServers((IServerType) parentElement).toArray();
		}
		if (parentElement instanceof ITreeContainerNode) {
			return ((ITreeContainerNode) parentElement).getChildren().toArray();
		}
		return EMPTY_ARRAY;
	}

	public Object getParent(Object element) {
		if (element instanceof ITreeSimpleNode) {
			return ((ITreeSimpleNode) element).getParent();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof IServerType) {
			return true;
		}
		if (element instanceof ITreeSimpleNode) {
			ITreeSimpleNode node = (ITreeSimpleNode) element;
			switch (node.getType()) {
			case NodeTypeConstants.Server:
				return ((IServer) element).isConnected();
			default:
				return element instanceof TreeContainerNode;
			}
		}
		return false;
	}
}
