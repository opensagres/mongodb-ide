import java.io.File;

import fr.opensagres.mongodb.process.shell.MongoShell;
import fr.opensagres.mongodb.process.shell.MongoShellFactory;


public class MongoShellTest {

	public static void main(String[] args) throws Exception {
		File mongoBaseDir = new File("D:\\MongoDB\\mongodb-win32-i386-2.0.7-rc0\\bin");
		
		MongoShellFactory factory = new MongoShellFactory(mongoBaseDir);
		MongoShell shell = factory.create();		
		
		String s = shell.getVersion();
		System.err.println(s);
		
		shell.start();
		
		
	}
}
