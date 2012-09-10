package fr.opensagres.mongodb.ide.demo.internal;

import java.net.URL;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.mongodb.MongoURI;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.orientdb.core.model.OrientServer;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private IServer mongoServer;
	private IServer orientServer;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		if (!hasMongoHQServer()) {
			mongoServer = new MongoServer("MongoHQ", new MongoURI(
					"mongodb://a:a@staff.mongohq.com:10093/testangelo"));
			Platform.getServerManager().addServer(mongoServer);
		}
		if (!hasOnlineOrientDBStudioServer()) {
			orientServer = new OrientServer("Online OrientDB Studio", new URL(
					"http://www.moobilis.com:2480/"));
			Platform.getServerManager().addServer(orientServer);
		}
	}

	private boolean hasMongoHQServer() {
		List<IServer> servers = Platform.getServerManager().getServers();
		for (IServer server : servers) {
			if ("MongoHQ".equals(server.getName())) {
				return true;
			}
		}
		return false;
	}

	private boolean hasOnlineOrientDBStudioServer() {
		List<IServer> servers = Platform.getServerManager().getServers();
		for (IServer server : servers) {
			if ("Online OrientDB Studio".equals(server.getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		if (mongoServer != null) {
			Platform.getServerManager().removeServer(mongoServer);
			mongoServer = null;
		}
		if (orientServer != null) {
			Platform.getServerManager().removeServer(orientServer);
			orientServer = null;
		}
		Activator.context = null;
	}

}
