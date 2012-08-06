/*******************************************************************************
 *  Copyright (c) 2007, 2008 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fr.opensagres.mongodb.ide.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public class ActionMenu extends Action implements IMenuCreator {

	private final List<Action> actions;
	private Menu menu;

	public ActionMenu(List<Action> actions) {
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
