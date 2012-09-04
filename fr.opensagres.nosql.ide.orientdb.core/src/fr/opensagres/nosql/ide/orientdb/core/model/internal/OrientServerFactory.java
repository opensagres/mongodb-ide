package fr.opensagres.nosql.ide.orientdb.core.model.internal;

import java.net.URL;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.extensions.AbstractServerFactory;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.orientdb.core.model.OrientServer;

public class OrientServerFactory extends AbstractServerFactory {

	@Override
	protected IServer doCreate(String id, String name, String url,
			Attributes attributes) throws Exception  {
		return new OrientServer(id, name, new URL(url));
	}
}
