package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.jface.viewers.ISelectionProvider;

import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.dialogs.StackTraceErrorDialog;

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
			try {
				shellRunnerType.getRunner().startShell(database);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		else {
			try {
				shellRunnerType.getRunner().stopShell(database);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
