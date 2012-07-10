package fr.opensagres.mongodb.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class ServersContentHandler extends AbstractContentHandler<Server> {

	public ServersContentHandler(Collection<Server> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("server".equals(localName)) {
			String name = attributes.getValue("name");
			String host = attributes.getValue("host");
			String p = attributes.getValue("port");
			Integer port = null;
			if (StringUtils.isNotEmpty(p)) {
				port = Integer.parseInt(p);
			}
			Server server = new Server(name, host, port);
			super.list.add(server);
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
