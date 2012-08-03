package fr.opensagres.mongodb.ide.ui.editors.server;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelEditorInput;

public class ServerEditorInput extends BasicModelEditorInput<Server> {

	public ServerEditorInput(Server server) {
		super(server);
	}

}
