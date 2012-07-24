package fr.opensagres.mongodb.process.shell;

import java.io.File;

import fr.opensagres.mongodb.process.AbstractMongoProcessFactory;

public class MongoShellFactory extends AbstractMongoProcessFactory {

	public MongoShellFactory(File mongoBaseDir) {
		super(mongoBaseDir, "mongo");
	}

	public MongoShell create() {
		return new MongoShell(this);
	}

}
