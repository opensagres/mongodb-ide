package fr.opensagres.mongodb.ide.ui.actions.database;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.actions.server.TreeNodeActionAdapter;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

public class ShellRunnerAction extends TreeNodeActionAdapter {

	private final IShellRunnerType shellRunnerType;
	private final boolean start;

	public ShellRunnerAction(IShellRunnerType shellRunnerType, boolean start,
			ISelectionProvider selectionProvider) {
		super(selectionProvider, start ? shellRunnerType.getStartName()
				: shellRunnerType.getStopName());
		this.start = start;
		this.shellRunnerType = shellRunnerType;
		if (start) {
			setImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_ELCL_START_SHELL));
			setHoverImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_CLCL_START_SHELL));
			setDisabledImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_DLCL_START_SHELL));
		} else {
			setImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_ELCL_STOP_SHELL));
			setHoverImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_CLCL_STOP_SHELL));
			setDisabledImageDescriptor(ImageResources
					.getImageDescriptor(ImageResources.IMG_DLCL_STOP_SHELL));
		}
	}

	@Override
	public boolean accept(Database database) {
		return shellRunnerType.getRunner().canSupport(database);
	}

	@Override
	public void perform(Database database) {
		if (start) {
			try {
				shellRunnerType.getRunner().startShell(database);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				shellRunnerType.getRunner().stopShell(database);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
