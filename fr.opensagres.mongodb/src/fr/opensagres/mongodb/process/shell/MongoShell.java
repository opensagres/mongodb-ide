package fr.opensagres.mongodb.process.shell;

import java.io.IOException;

import fr.opensagres.mongodb.helper.ProcessHelper;
import fr.opensagres.mongodb.process.AbstractMongoProcess;

public class MongoShell extends AbstractMongoProcess {

	public MongoShell(MongoShellFactory factory) {
		super(factory);
	}
	
	@Override
	public void start() throws IOException, InterruptedException {
		ProcessHelper.startShell(getFactory().getProcessFile());
	}
}
