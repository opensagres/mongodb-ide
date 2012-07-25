package fr.opensagres.mongodb.ide.ui.internal;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import fr.opensagres.mongodb.ide.core.model.Server;

public class ServerUI {

	/**
	 * Open the given server with the server editor.
	 * 
	 * @param server
	 */
	public static void editServer(Server server) {
		if (server == null)
			return;

		editServer(server.getId());
	}

	/**
	 * Open the given server id with the server editor.
	 * 
	 * @param serverId
	 */
	protected static void editServer(String serverId) {
		if (serverId == null)
			return;

		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();

//		try {
//			IServerEditorInput input = new ServerEditorInput(serverId);
//			page.openEditor(input, IServerEditorInput.EDITOR_ID);
//		} catch (Exception e) {
//			if (Trace.SEVERE) {
//				Trace.trace(Trace.STRING_SEVERE, "Error opening server editor",
//						e);
//			}
//		}
	}

}
