package fr.opensagres.nosql.ide.ui;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.nosql.ide.ui.editors.AbstractEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.ContextHandlerUtils;
import fr.opensagres.nosql.ide.ui.internal.Activator;

public class ServerUI {

	private static final String[] LOCALHOSTS = new String[] { "localhost",
			"127.0.0.1" };

	private static final String[] DEFAULT_PORTS = new String[] { "27017" };

	public static String[] getLocalhosts() {
		return LOCALHOSTS;
	}

	public static String[] getDefaultPorts() {
		return DEFAULT_PORTS;
	}

	public static void openEditor(AbstractEditorInput input, String editorId)
			throws PartInitException {
		IWorkbenchWindow workbenchWindow = Activator.getDefault()
				.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		IEditorPart part = page.openEditor(input, editorId);
		String activePageIdOnLoad = input.getActivePageIdOnLoad();
		if (StringUtils.isNotEmpty(activePageIdOnLoad)) {
			((FormEditor) part).setActivePage(activePageIdOnLoad);
		}
	}

	public static void openEditor(ITreeSimpleNode node) {
		String commandId = getCommandId(node);
		if (StringUtils.isNotEmpty(commandId)) {
			try {
				IWorkbenchWindow workbenchWindow = Activator.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = workbenchWindow.getActivePage();
				ContextHandlerUtils.executeCommand(commandId, workbenchWindow,
						node);
			} catch (Exception e) {
				e.printStackTrace();
				// StackTraceErrorDialog.openError(parentShell, "TODO", "TODO",
				// e);
			}
		}
	}

	private static String getCommandId(ITreeSimpleNode node) {
		IServerType serverType = node.getServer().getServerType();
		if (serverType == null) {
			return null;
		}
		return Platform.getCommandIdProviderRegistry().getCommandId(serverType,
				ICommandIdProvider.OPEN_EDITOR, node);
	}

}
