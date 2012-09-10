package fr.opensagres.nosql.ide.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IServiceLocator;

public class ContextHandlerUtils {

	public static Object executeCommand(String commandId,
			IServiceLocator locator, Object model) throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		Event e = new ContextHandlerEvent(model);
		return executeCommand(commandId, locator, e);
	}

	public static Object executeCommand(String commandId,
			IServiceLocator locator, Event e) throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		IHandlerService handlerService = (IHandlerService) locator
				.getService(IHandlerService.class);
		return handlerService.executeCommand(commandId, e);
	}

	/**
	 * Opens an editor on the given input.
	 * <p>
	 * If this page already has an editor open on the target input that editor
	 * is brought to the front; otherwise, a new editor is opened. Two editor
	 * inputs are considered the same if they equal. See
	 * <code>Object.equals(Object)<code>
	 * and <code>IEditorInput</code>. If <code>activate == true</code> the
	 * editor will be activated.
	 * </p>
	 * <p>
	 * The editor type is determined by mapping <code>editorId</code> to an
	 * editor extension registered with the workbench. An editor id is passed
	 * rather than an editor object to prevent the accidental creation of more
	 * than one editor for the same input. It also guarantees a consistent
	 * lifecycle for editors, regardless of whether they are created by the user
	 * or restored from saved data.
	 * </p>
	 * 
	 * @param event
	 *            the event from the {@link AbstractHandler} commands.
	 * @param input
	 *            the editor input
	 * @param editorId
	 *            the id of the editor extension to use
	 * @param activate
	 *            if <code>true</code> the editor will be activated
	 * @return an open editor, or <code>null</code> if an external editor was
	 *         opened
	 * @exception PartInitException
	 *                if the editor could not be created or initialized
	 */
	public static IEditorPart openEditor(ExecutionEvent event,
			IEditorInput input, String editorId, boolean activate)
			throws PartInitException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (window == null) {
			IWorkbench wb = PlatformUI.getWorkbench();
			window = wb.getActiveWorkbenchWindow();
		}
		IWorkbenchPage page = window.getActivePage();
		return page.openEditor(input, editorId, activate);
	}

	// /**
	// *
	// * @param event
	// * @param file
	// * @param activate
	// * @return
	// * @throws PartInitException
	// */
	// public static IEditorPart openSystemExternalEditor(ExecutionEvent event,
	// File file, boolean activate) throws PartInitException {
	// IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
	// IWorkbenchPage page = window.getActivePage();
	// return openSystemExternalEditor(page, file, activate);
	// }

	// /**
	// *
	// * @param event
	// * @param file
	// * @param activate
	// * @return
	// * @throws PartInitException
	// */
	// public static IEditorPart openSystemExternalEditor(IWorkbenchPage page,
	// File file, boolean activate) throws PartInitException {
	// return page.openEditor(new SystemFileEditorInput(file),
	// IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID, activate);
	// }
}
