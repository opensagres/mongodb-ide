package fr.opensagres.nosql.ide.core.extensions;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.model.IServer;

public interface IServerFactory {

	IServer create(Attributes attributes);

}
