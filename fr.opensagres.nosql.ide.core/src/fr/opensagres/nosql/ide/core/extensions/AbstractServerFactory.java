package fr.opensagres.nosql.ide.core.extensions;

import org.xml.sax.Attributes;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.settings.ServersConstants;

public abstract class AbstractServerFactory implements IServerFactory {

	public IServer create(Attributes attributes) {
		String id = attributes.getValue(ServersConstants.ID_ATTR);
		String name = attributes.getValue(ServersConstants.NAME_ATTR);
		String url = attributes.getValue(ServersConstants.URL_ATTR);
		IServer server = doCreate(id, name, url, attributes);
		if (server != null) {
			String runtimeId = attributes
					.getValue(ServersConstants.RUNTIME_ID_ATTR);
			IServerRuntime runtime = Platform.getServerRuntimeManager().findRuntime(
					runtimeId);
			server.setRuntime(runtime);
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
		}
		return server;
	}

	protected abstract IServer doCreate(String id, String name, String url,
			Attributes attributes);
}
