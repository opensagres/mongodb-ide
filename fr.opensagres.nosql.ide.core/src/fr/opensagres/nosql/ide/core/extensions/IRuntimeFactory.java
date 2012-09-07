package fr.opensagres.nosql.ide.core.extensions;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;

public interface IRuntimeFactory {

	IServerRuntime create(Attributes attributes) throws Exception;

}
