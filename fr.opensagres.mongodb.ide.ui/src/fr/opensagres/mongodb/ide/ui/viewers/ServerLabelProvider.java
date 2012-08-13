package fr.opensagres.mongodb.ide.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;

import fr.opensagres.mongodb.ide.core.model.Server;

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
		return ((Server) element).getName();
	}

}
