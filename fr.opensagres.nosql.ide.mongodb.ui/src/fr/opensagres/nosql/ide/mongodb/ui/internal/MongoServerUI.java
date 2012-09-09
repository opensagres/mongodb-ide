package fr.opensagres.nosql.ide.mongodb.ui.internal;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server.ServerEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.server.ServerEditorInput;
import fr.opensagres.nosql.ide.ui.ServerUI;

public class MongoServerUI {

	public static void editServer(IServer server) {
		if (server == null)
			return;

		try {
			ServerEditorInput input = new ServerEditorInput(
					(MongoServer) server);
			ServerUI.openEditor(input, ServerEditor.ID);
		} catch (Exception e) {
			if (Trace.SEVERE) {
				Trace.trace(Trace.STRING_SEVERE, "Error opening server editor",
						e);
			}
		}
	}
}
