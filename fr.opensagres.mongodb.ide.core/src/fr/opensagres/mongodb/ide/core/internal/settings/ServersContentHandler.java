package fr.opensagres.mongodb.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.mongodb.MongoURI;

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
			String mongoURI = attributes
					.getValue(ServersConstants.MONGO_URI_ATTR);
			if (!StringUtils.isEmpty(mongoURI)) {
				String id = attributes.getValue(ServersConstants.ID_ATTR);
				String name = attributes.getValue(ServersConstants.NAME_ATTR);
				Server server = new Server(id, name, new MongoURI(mongoURI));
				String runtimeId = attributes
						.getValue(ServersConstants.RUNTIME_ID_ATTR);
				MongoRuntime runtime = Platform.getMongoRuntimeManager()
						.findRuntime(runtimeId);
				server.setRuntime(runtime);
				super.list.add(server);
			}
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
