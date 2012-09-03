package fr.opensagres.nosql.ide.core.extensions;

import java.util.Collection;

public interface IServerTypeRegistry extends IRegistry {

	/**
	 * Return the {@link IServerType} retrieved by the given id.
	 * 
	 * @param id
	 * @return
	 */
	IServerType getType(String id);

	Collection<IServerType> getTypes();
}
