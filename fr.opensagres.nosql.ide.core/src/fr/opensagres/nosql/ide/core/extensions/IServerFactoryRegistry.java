package fr.opensagres.nosql.ide.core.extensions;

import java.util.Collection;

public interface IServerFactoryRegistry extends IRegistry {

	/**
	 * Return the {@link IServerFactory} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	IServerFactory getFactory(IServerType serverType);

	/**
	 * Return the list of the {@link IServerFactory}.
	 * 
	 * @return
	 */
	Collection<IServerFactory> getFactories();
}
