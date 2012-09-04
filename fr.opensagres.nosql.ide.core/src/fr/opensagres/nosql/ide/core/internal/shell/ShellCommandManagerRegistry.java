package fr.opensagres.nosql.ide.core.internal.shell;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.nosql.ide.core.shell.IShellCommandListener;
import fr.opensagres.nosql.ide.core.shell.IShellCommandListenerAware;
import fr.opensagres.nosql.ide.core.shell.IShellCommandManagerRegistry;

public class ShellCommandManagerRegistry extends
		ArrayList<IShellCommandListenerAware> implements
		IShellCommandManagerRegistry {

	private static final IShellCommandManagerRegistry INSTANCE = new ShellCommandManagerRegistry();

	private final List<IShellCommandListenerAware> managers;

	public ShellCommandManagerRegistry() {
		this.managers = new ArrayList<IShellCommandListenerAware>();
	}

	public static IShellCommandManagerRegistry getInstance() {
		return INSTANCE;
	}

	public void addShellListener(IShellCommandListener listener) {
		for (IShellCommandListenerAware manager : this) {
			manager.addShellListener(listener);
		}
	}

	public void removeShellListener(IShellCommandListener listener) {
		for (IShellCommandListenerAware manager : this) {
			manager.removeShellListener(listener);
		}
	}

	public void addShellListener(IShellCommandListener listener, int eventMask) {
		for (IShellCommandListenerAware manager : this) {
			manager.addShellListener(listener, eventMask);
		}
	}

	@Override
	public boolean add(IShellCommandListenerAware e) {
		return super.add(e);
	}

	public boolean remove(IShellCommandListenerAware manager) {
		return super.remove(manager);
	}
}
