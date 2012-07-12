package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class StartServerAction extends AbstractServerAction {

	public StartServerAction(Shell shell, ISelectionProvider selectionProvider) {
		super(shell, selectionProvider, "start");
		setToolTipText(Messages.actionStartToolTip);
		setText(Messages.actionStart);
		setImageDescriptor(ImageResources.getImageDescriptor(ImageResources.IMG_ELCL_START));
		setHoverImageDescriptor(ImageResources.getImageDescriptor(ImageResources.IMG_CLCL_START));
		setDisabledImageDescriptor(ImageResources.getImageDescriptor(ImageResources.IMG_DLCL_START));
		// setActionDefinitionId("org.eclipse.wst.server.run");
		try {
			selectionChanged((IStructuredSelection) selectionProvider
					.getSelection());
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Update the name of the Action label, depending on the status of the
	 * server.
	 * 
	 * @param sel
	 *            the IStructuredSelection from the view
	 */
	private void updateText(IStructuredSelection sel) {
		if (sel.isEmpty()) {
			setText(Messages.actionStart);
			return;
		}
		Iterator iterator = sel.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj instanceof Server) {
				Server server = (Server) obj;
				if (server.getServerState() == ServerState.Started
						|| server.getServerState() == ServerState.Starting) {
					setText(Messages.actionRestart);
					setToolTipText(Messages.actionRestartToolTip);
				} else {
					setText(Messages.actionStart);
					setToolTipText(Messages.actionStartToolTip);
				}
			}
		}
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	public boolean accept(Server server) {
//		if (server.getServerState() != ServerState.Started) { // start
//			return server.canStart(launchMode).isOK();
//		}
//		// restart
//		String mode2 = launchMode;
//		if (mode2 == null)
//			mode2 = server.getMode();
//		return server.getServerType() != null
//				&& UIDecoratorManager.getUIDecorator(server.getServerType())
//						.canRestart() && server.canRestart(mode2).isOK();
		return true;
	}

	/**
	 * Perform action on this server.
	 * 
	 * @param server
	 *            a server
	 */
	public void perform(Server server) {
		start(server, shell);
	}

	public static void start(Server server, final Shell shell) {
		if (server.getServerState() != ServerState.Started) {
			try {
				server.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if (!ServerUIPlugin.saveEditors())
//				return;

			/*
			 * final IAdaptable info = new IAdaptable() { public Object
			 * getAdapter(Class adapter) { if (Shell.class.equals(adapter))
			 * return shell; return null; } };
			 */
//			server.start(launchMode, (IOperationListener) null);
		} 
//		else {
//			if (shell != null && !ServerUIPlugin.promptIfDirty(shell, server))
//				return;
//
//			try {
//				String launchMode2 = launchMode;
//				if (launchMode2 == null)
//					launchMode2 = server.getMode();
//				server.restart(launchMode2, (IOperationListener) null);
//			} catch (Exception e) {
//				if (Trace.SEVERE) {
//					Trace.trace(Trace.STRING_SEVERE, "Error restarting server",
//							e);
//				}
//			}
//		}
	}

	public void selectionChanged(IStructuredSelection sel) {
		super.selectionChanged(sel);
		updateText(sel);
	}
}
