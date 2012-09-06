package fr.opensagres.nosql.ide.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;

public class ServerTypeLabelProvider extends LabelProvider {

	private static ServerTypeLabelProvider instance;

	public static ServerTypeLabelProvider getInstance() {
		synchronized (ServerTypeLabelProvider.class) {
			if (instance == null) {
				instance = new ServerTypeLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		return ((IServerType) element).getName();
	}

}
