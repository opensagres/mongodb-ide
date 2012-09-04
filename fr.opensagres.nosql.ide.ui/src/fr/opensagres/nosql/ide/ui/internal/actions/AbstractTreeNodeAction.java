package fr.opensagres.nosql.ide.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.SelectionProviderAction;

public abstract class AbstractTreeNodeAction extends SelectionProviderAction {

	protected AbstractTreeNodeAction(ISelectionProvider provider, String text) {
		super(provider, text);
		setEnabled(false);
	}

	public void run() {
		IStructuredSelection selection = getStructuredSelection();
		if (selection.isEmpty())
			return;
		Object obj = selection.getFirstElement();
		if (accept(obj)) {
			perform(obj);
		}
		selectionChanged(selection);
	}

	/**
	 * Update the enabled state.
	 * 
	 * @param sel
	 *            a selection
	 */
	@Override
	public void selectionChanged(IStructuredSelection sel) {
		if (sel.isEmpty()) {
			setEnabled(false);
			return;
		}
		boolean enabled = false;
		Iterator iterator = sel.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (accept(obj)) {
				enabled = true;
			}
		}
		setEnabled(enabled);
	}

	protected abstract boolean accept(Object obj);

	protected abstract void perform(Object obj);

}
