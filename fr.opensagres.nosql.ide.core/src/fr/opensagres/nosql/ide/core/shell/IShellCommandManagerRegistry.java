package fr.opensagres.nosql.ide.core.shell;

public interface IShellCommandManagerRegistry extends
		IShellCommandListenerAware {

	boolean add(IShellCommandListenerAware manager);

	boolean remove(IShellCommandListenerAware manager);
}
