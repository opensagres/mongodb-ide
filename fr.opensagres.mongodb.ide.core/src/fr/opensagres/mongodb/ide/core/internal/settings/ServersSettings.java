package fr.opensagres.mongodb.ide.core.internal.settings;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;

import fr.opensagres.mongodb.ide.core.model.Server;

public class ServersSettings extends AbstractSettings<Server> {

	private static final ServersSettings INSTANCE = new ServersSettings();

	public static ServersSettings getInstance() {
		return INSTANCE;
	}

	@Override
	protected ContentHandler createContentHandler(Collection<Server> list) {
		return new ServersContentHandler(list);
	}

	@Override
	protected String getXMLRootElementName() {
		return ServersConstants.SERVERS_ELT;
	}

	@Override
	protected void save(Server server, Writer writer) throws IOException {
		server.save(writer);
	}

}
