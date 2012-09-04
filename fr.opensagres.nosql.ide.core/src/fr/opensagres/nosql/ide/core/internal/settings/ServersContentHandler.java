package fr.opensagres.nosql.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerFactory;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.internal.Trace;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.settings.ServersConstants;
import fr.opensagres.nosql.ide.core.utils.StringUtils;

public class ServersContentHandler extends AbstractContentHandler<IServer> {

	public ServersContentHandler(Collection<IServer> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (ServersConstants.SERVER_ELT.equals(localName)) {
			String serverTypeId = attributes
					.getValue(ServersConstants.SERVER_TYPE_ID_ATTR);
			if (!StringUtils.isEmpty(serverTypeId)) {
				IServerType serverType = Platform.getServerTypeRegistry()
						.getType(serverTypeId);
				if (serverType != null) {
					IServerFactory factory = Platform
							.getServerFactoryRegistry().getFactory(serverType);
					if (factory != null) {
						try {
							IServer server = factory.create(attributes);
							if (server != null) {
								super.list.add(server);
							}
						} catch (Exception e) {
							Trace.trace(Trace.STRING_SEVERE, e.getMessage());
						}
					}
				}
			}

			// String mongoURI = attributes
			// .getValue(ServersConstants.MONGO_URI_ATTR);
			// if (!StringUtils.isEmpty(mongoURI)) {
			// String id = attributes.getValue(ServersConstants.ID_ATTR);
			// String name = attributes.getValue(ServersConstants.NAME_ATTR);
			// // Server server = new Server(id, name, new MongoURI(mongoURI));
			// // String runtimeId = attributes
			// // .getValue(ServersConstants.RUNTIME_ID_ATTR);
			// // MongoRuntime runtime = Platform.getMongoRuntimeManager()
			// // .findRuntime(runtimeId);
			// // server.setRuntime(runtime);
			// // super.list.add(server);
			// }
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
