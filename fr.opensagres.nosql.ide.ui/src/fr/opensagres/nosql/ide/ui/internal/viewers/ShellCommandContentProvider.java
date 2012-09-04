package fr.opensagres.nosql.ide.ui.internal.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;

public class ShellCommandContentProvider extends ArrayContentProvider {

	// private static final Object[] EMPTY_ARRAY = new Object[0];

	private static ShellCommandContentProvider instance;

	public static ShellCommandContentProvider getInstance() {
		synchronized (ShellCommandContentProvider.class) {
			if (instance == null) {
				instance = new ShellCommandContentProvider();
			}
			return instance;
		}
	}

}
