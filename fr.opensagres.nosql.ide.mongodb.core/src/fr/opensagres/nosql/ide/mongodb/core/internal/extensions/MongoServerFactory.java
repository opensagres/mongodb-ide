package fr.opensagres.nosql.ide.mongodb.core.internal.extensions;

import org.xml.sax.Attributes;

import com.mongodb.MongoURI;

import fr.opensagres.nosql.ide.core.extensions.AbstractServerFactory;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;

public class MongoServerFactory extends AbstractServerFactory {

	@Override
	protected IServer doCreate(String id, String name, String url,
			Attributes attributes) {
		MongoURI mongoURI = new MongoURI(url);
		return new MongoServer(id, name, mongoURI);
	}
}
