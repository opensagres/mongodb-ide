package fr.opensagres.nosql.ide.ui.internal.actions;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public abstract class AbstractTreeNodeActionGroup extends
		AbstractTreeNodeAction implements IMenuCreator {

	private Menu menu;

	protected AbstractTreeNodeActionGroup(ISelectionProvider provider,
			String text) {
		super(provider, text);
	}

	@Override
	protected void perform(Object obj) {
		Action runAction = null;
		if (obj != null) {
			runAction = getDefaultAction(obj);
		}
		if (runAction == null) {
			Collection<Action> actions = getActions(obj);
			runAction = getFirstAction(actions);
		}
		if (runAction != null) {
			runAction.run();
		}
	}

	private Action getFirstAction(Collection<Action> actions) {
		if (actions == null) {
			return null;
		}
		for (Action action : actions) {
			return action;
		}
		return null;
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

		Collection<Action> actions = getActions();
		if (actions != null) {
			for (Action action : actions) {
				addActionToMenu(menu, action);
			}
		}
		return menu;
	}

	private Collection<Action> getActions() {
		Object selectedElement = getSelectedElement();
		if (selectedElement == null) {
			return Collections.emptyList();
		}
		return getActions(selectedElement);
	}

	private Object getSelectedElement() {
		IStructuredSelection selection = getStructuredSelection();
		if (!selection.isEmpty()) {
			return selection.getFirstElement();
		}
		return null;
	}

	@Override
	protected boolean accept(Object obj) {
		if (obj == null) {
			return false;
		}
		Collection<Action> actions = getActions(obj);
		if (!(actions != null && actions.size() > 0)) {
			return false;
		}
		for (Action action : actions) {
			if (((AbstractTreeNodeAction) action).accept(obj)) {
				return true;
			}
		}
		return false;
	}

	protected abstract Collection<Action> getActions(Object obj);

	protected abstract Action getDefaultAction(Object obj);

	public Menu getMenu(Menu parent) {
		return null;
	}

	private void addActionToMenu(Menu parent, Action action) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(parent, -1);
	}
}
