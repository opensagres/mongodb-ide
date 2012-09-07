package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.extensions.AbstractRuntimeFactory;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServerRuntime;

public class MongoRuntimeFactory extends AbstractRuntimeFactory{

	@Override
	protected IServerRuntime doCreate(String id, String name, String path,
			Attributes attributes) throws Exception {
		return new MongoServerRuntime(id, name, path);
	}

}
