package fr.opensagres.mongodb.process;

import java.io.IOException;

public abstract class AbstractMongoProcess {

	private final AbstractMongoProcessFactory factory;

	public AbstractMongoProcess(AbstractMongoProcessFactory factory) {
		this.factory = factory;
	}

	public AbstractMongoProcessFactory getFactory() {
		return factory;
	}

	public String getVersion() throws IOException {
		return factory.getVersion();
	}
	
	public abstract void start() throws IOException, InterruptedException;
}
