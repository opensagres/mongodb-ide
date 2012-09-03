package fr.opensagres.nosql.ide.core.internal.settings;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.settings.ServersConstants;

public class ServersSettings extends AbstractSettings<IServer> {

	private static final ServersSettings INSTANCE = new ServersSettings();

	public static ServersSettings getInstance() {
		return INSTANCE;
	}

	@Override
	protected ContentHandler createContentHandler(Collection<IServer> list) {
		return new ServersContentHandler(list);
	}

	@Override
	protected String getXMLRootElementName() {
		return ServersConstants.SERVERS_ELT;
	}

	@Override
	protected void save(IServer server, Writer writer) throws IOException {
		server.save(writer);
	}

}
