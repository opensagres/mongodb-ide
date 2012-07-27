package fr.opensagres.mongodb.ide.ui.editors.collection;

import org.eclipse.ui.PartInitException;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormEditor;
import fr.opensagres.mongodb.ide.ui.internal.Trace;

public class CollectionEditor extends AbstractFormEditor {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.collection.CollectionEditor";

	@Override
	protected void addPages() {
		try {
			super.addPage(new OverviewPage(this));
			super.addPage(new DocumentsPage(this));
		} catch (PartInitException e) {
			Trace.trace(Trace.STRING_SEVERE,
					"Error while adding page in the editor ", e);
		}
	}
}
