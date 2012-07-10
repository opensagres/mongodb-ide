package fr.opensagres.mongodb.ide.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class DeleteAction extends Action {

	private final TreeViewer viewer;

	public DeleteAction(TreeViewer viewer) {
		super.setText(Messages.DeleteAction_text);
		super.setToolTipText(Messages.DeleteAction_toolTipText);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DELETE_16));
		this.viewer = viewer;
	}

	public void run() {
		ITreeSelection selection = (ITreeSelection) viewer.getSelection();
		if (selection != null && !selection.isEmpty()) {
			Iterator iter = selection.iterator();
			while (iter.hasNext()) {
				Object element = iter.next();
				if (element instanceof Server) {
					try {
						Platform.getServerManager().removeServer((Server) element);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// TODO manag ethe other delete by updating the Database.
				}
			}
		}
	}

}
