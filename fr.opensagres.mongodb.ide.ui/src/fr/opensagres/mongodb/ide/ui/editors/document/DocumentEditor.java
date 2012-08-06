package fr.opensagres.mongodb.ide.ui.editors.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.mongodb.ide.core.model.Document;
import fr.opensagres.mongodb.ide.ui.editors.ModelFormEditor;

public class DocumentEditor extends
		ModelFormEditor<DocumentEditorInput, Document> {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.document.DocumentEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
		super.addPage(new JSONTextPage(this));
	}

	@Override
	protected String getActivePageIdOnLoad() {
		return JSONTextPage.ID;
	}

	@Override
	protected Document onLoad(DocumentEditorInput input) throws Exception {
		return input.getModel();
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
