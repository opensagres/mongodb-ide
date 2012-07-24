package fr.opensagres.mongodb.process.server;

import java.io.File;

import fr.opensagres.mongodb.process.AbstractMongoProcessFactory;

public class MongoServerFactory extends AbstractMongoProcessFactory {

	public MongoServerFactory(File mongoBaseDir) {
		super(mongoBaseDir, "mongod");
	}

	public MongoServer create() {
		return create(null);
	}

	public MongoServer create(Integer port) {
		return new MongoServer(this, port);
	}

}
