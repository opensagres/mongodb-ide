package fr.opensagres.mongodb.ide.core.internal.settings;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
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
		return "servers";
	}
	
	@Override
	protected void save(Server t, Writer writer) throws IOException {
		writer.append("<server");
		super.writeAttr("name", t.getName(), writer);
		super.writeAttr("host", t.getHost(), writer);
		super.writeAttr("port", t.getPort(), writer);
		writer.append("/>");

	}

}
