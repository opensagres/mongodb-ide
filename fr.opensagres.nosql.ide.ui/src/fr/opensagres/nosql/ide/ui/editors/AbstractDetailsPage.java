package fr.opensagres.nosql.ide.ui.editors;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;

public abstract class AbstractDetailsPage extends AbstractFormPart implements
		IDetailsPage {

	public void selectionChanged(IFormPart part, ISelection selection) {
		Object modelObject = null;
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1) {
			modelObject = ssel.getFirstElement();
		}
		refresh(modelObject);
	}

	protected abstract void refresh(Object modelObject);
	
}
