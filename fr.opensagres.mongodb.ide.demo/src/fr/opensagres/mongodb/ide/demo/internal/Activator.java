package fr.opensagres.mongodb.ide.demo.internal;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.mongodb.MongoURI;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private Server server;

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
			server = new Server("MongoHQ", new MongoURI(
					"mongodb://a:a@staff.mongohq.com:10093/testangelo"));
			Platform.getServerManager().addServer(server);
		}
	}

	private boolean hasMongoHQServer() {
		List<Server> servers = Platform.getServerManager().getServers();
		for (Server server : servers) {
			if ("MongoHQ".equals(server.getName())) {
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
		if (server != null) {
			Platform.getServerManager().removeServer(server);
			server = null;
		}
		Activator.context = null;
	}

}
