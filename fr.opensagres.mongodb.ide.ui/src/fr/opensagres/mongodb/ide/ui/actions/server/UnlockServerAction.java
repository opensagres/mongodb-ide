package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class UnlockServerAction extends AbstractServerAction {

	public UnlockServerAction(Shell shell, ISelectionProvider selectionProvider) {
		super(shell, selectionProvider, "unlock");
		setToolTipText(Messages.actionUnlockToolTip);
		setText(Messages.actionUnlock);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_START));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_START));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_START));
		try {
			selectionChanged((IStructuredSelection) selectionProvider
					.getSelection());
		} catch (Exception e) {
			// ignore
		}
	}

	@Override
	public void perform(Server server) {
		server.unlock();
	}

	@Override
	public void perform(Database database) {

	}

	@Override
	public boolean accept(Server server) {
		return server.isLocked();
	}

}
