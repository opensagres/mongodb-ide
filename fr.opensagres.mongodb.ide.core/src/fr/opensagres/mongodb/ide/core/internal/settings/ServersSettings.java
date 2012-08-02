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
	protected void save(Server t, Writer writer) throws IOException {
		writer.append("<");
		writer.append(ServersConstants.SERVER_ELT);
		super.writeAttr(ServersConstants.ID_ATTR, t.getId(), writer);
		super.writeAttr(ServersConstants.NAME_ATTR, t.getName(), writer);
		super.writeAttr(ServersConstants.MONGO_URI_ATTR, t.getMongoURI()
				.toString(), writer);
		if (t.getRuntime() != null) {
			super.writeAttr(ServersConstants.RUNTIME_ID_ATTR, t.getRuntime()
					.getId(), writer);
		}
		writer.append("/>");

	}

}
