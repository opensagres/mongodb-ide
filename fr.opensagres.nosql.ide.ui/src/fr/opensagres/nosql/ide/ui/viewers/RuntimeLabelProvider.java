package fr.opensagres.nosql.ide.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;

public class RuntimeLabelProvider extends LabelProvider {

	private static RuntimeLabelProvider instance;

	public static RuntimeLabelProvider getInstance() {
		synchronized (RuntimeLabelProvider.class) {
			if (instance == null) {
				instance = new RuntimeLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		return ((IServerRuntime) element).getName();
	}

}
