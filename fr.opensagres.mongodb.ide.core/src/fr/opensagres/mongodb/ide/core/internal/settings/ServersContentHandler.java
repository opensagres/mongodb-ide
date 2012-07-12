package fr.opensagres.mongodb.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class ServersContentHandler extends AbstractContentHandler<Server> {

	public ServersContentHandler(Collection<Server> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (ServersConstants.SERVER_ELT.equals(localName)) {
			String id = attributes.getValue(ServersConstants.ID_ATTR);
			String name = attributes.getValue(ServersConstants.NAME_ATTR);
			String host = attributes.getValue(ServersConstants.HOST_ATTR);
			String p = attributes.getValue(ServersConstants.PORT_ATTR);
			Integer port = null;
			if (StringUtils.isNotEmpty(p)) {
				port = Integer.parseInt(p);
			}
			Server server = new Server(id, name, host, port);
			String runtimeId = attributes
					.getValue(ServersConstants.RUNTIME_ID_ATTR);
			MongoRuntime runtime = Platform.getMongoRuntimeManager()
					.findRuntime(runtimeId);
			server.setRuntime(runtime);
			super.list.add(server);
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
