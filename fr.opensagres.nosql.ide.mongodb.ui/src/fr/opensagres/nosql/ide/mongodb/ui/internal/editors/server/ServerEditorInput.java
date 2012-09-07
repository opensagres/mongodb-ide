package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server;

import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.ui.editors.BasicModelEditorInput;

public class ServerEditorInput extends BasicModelEditorInput<MongoServer> {

	public ServerEditorInput(MongoServer server) {
		super(server);
	}

}
