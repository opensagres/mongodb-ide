package fr.opensagres.nosql.ide.core.extensions;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.settings.RuntimesConstants;

public abstract class AbstractRuntimeFactory implements IRuntimeFactory {

	public IServerRuntime create(Attributes attributes) throws Exception {
		String id = attributes.getValue(RuntimesConstants.ID_ATTR);
		String name = attributes.getValue(RuntimesConstants.NAME_ATTR);
		String path = attributes.getValue(RuntimesConstants.PATH_ATTR);
		return doCreate(id, name, path, attributes);
	}

	protected abstract IServerRuntime doCreate(String id, String name,
			String path, Attributes attributes) throws Exception;
}
