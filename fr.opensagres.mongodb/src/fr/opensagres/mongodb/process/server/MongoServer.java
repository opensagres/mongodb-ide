package fr.opensagres.mongodb.process.server;

import java.io.IOException;

import fr.opensagres.mongodb.helper.ProcessHelper;
import fr.opensagres.mongodb.process.AbstractMongoProcess;

public class MongoServer extends AbstractMongoProcess {

	private final Integer port;

	public MongoServer(MongoServerFactory factory, Integer port) {
		super(factory);
		this.port = port;
	}

	@Override
	public void start() throws IOException, InterruptedException {
		ProcessHelper.startServer(getFactory().getProcessFile(), port);
	}

	public void stop() throws Exception {

	}

	public void join() {
		// TODO Auto-generated method stub

	}

}
