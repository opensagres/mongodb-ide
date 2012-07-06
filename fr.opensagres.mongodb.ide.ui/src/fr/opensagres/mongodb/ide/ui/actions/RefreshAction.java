package fr.opensagres.mongodb.ide.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class RefreshAction extends Action {

	private final TreeViewer viewer;

	public RefreshAction(TreeViewer viewer) {
		super.setText(Messages.RefreshAction_text);
		super.setToolTipText(Messages.RefreshAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_REFRESH_16));
		this.viewer = viewer;
	}

	public void run() {
		viewer.refresh();
	}

}
