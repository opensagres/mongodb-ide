package fr.opensagres.mongodb.ide.ui.actions.server;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractTreeNodeActionGroup extends
		AbstractTreeNodeAction implements IMenuCreator {

	protected final List<Action> actions;
	private Menu menu;

	public AbstractTreeNodeActionGroup(Shell shell,
			ISelectionProvider selectionProvider, List<Action> actions) {
		super(shell, selectionProvider, "");
		this.actions = actions;
		if (this.actions.size() > 0) {
			Action firstAction = this.actions.get(0);
			setToolTipText(firstAction.getToolTipText());
			setImageDescriptor(firstAction.getImageDescriptor());
			if (this.actions.size() > 1)
				setMenuCreator(this);
		}
	}

	public void run() {
		if (this.actions.size() > 0) {
			Action firstAction = this.actions.get(0);
			firstAction.run();
		}
	}

	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	public Menu getMenu(Control parent) {
		if (menu != null)
			menu.dispose();
		menu = new Menu(parent);

		for (Action action : this.actions) {
			addActionToMenu(menu, action);
		}
		return menu;
	}

	public Menu getMenu(Menu parent) {
		return null;
	}

	protected void addActionToMenu(Menu parent, Action action) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(parent, -1);
	}

}
