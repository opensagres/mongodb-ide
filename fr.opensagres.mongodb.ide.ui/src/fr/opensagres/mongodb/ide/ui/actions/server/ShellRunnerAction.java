package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.model.Database;

public class ShellRunnerAction extends TreeNodeActionAdapter {

	private final IShellRunnerType shellRunnerType;
	private final boolean start;

	public ShellRunnerAction(IShellRunnerType shellRunnerType, boolean start,
			ISelectionProvider selectionProvider) {
		super(selectionProvider, start ? shellRunnerType.getStartName()
				: shellRunnerType.getStopName());
		this.start = start;
		this.shellRunnerType = shellRunnerType;
	}

	@Override
	protected boolean accept(Database database) {
		return shellRunnerType.getRunner().canSupport(database);
	}

	@Override
	protected void perform(Database database) {
		if (start) {
			shellRunnerType.getRunner().startShell(database);
		}
		else {
			shellRunnerType.getRunner().stopShell(database);
		}
	}

}
