import java.io.File;

import fr.opensagres.mongodb.process.server.MongoServer;
import fr.opensagres.mongodb.process.server.MongoServerFactory;

public class MongoServerTest {

	public static void main(String[] args) throws Exception {
		File mongoBaseDir = new File(
				"D:\\MongoDB\\mongodb-win32-i386-2.0.7-rc0\\bin");
		MongoServerFactory factory = new MongoServerFactory(mongoBaseDir);
		MongoServer server = factory.create(8080);

		String s = server.getVersion();
		System.err.println(s);

		server.start();
		server.join();
	}
}
