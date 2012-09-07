package fr.opensagres.nosql.ide.core.extensions;

import java.util.Collection;

public interface IRuntimeFactoryRegistry extends IRegistry {

	/**
	 * Return the {@link IRuntimeFactory} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	IRuntimeFactory getFactory(IServerType serverType);

	/**
	 * Return the list of the {@link IRuntimeFactory}.
	 * 
	 * @return
	 */
	Collection<IRuntimeFactory> getFactories();
}
