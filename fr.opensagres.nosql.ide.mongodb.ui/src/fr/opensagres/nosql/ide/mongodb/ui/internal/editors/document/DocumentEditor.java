package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.nosql.ide.mongodb.core.model.Document;
import fr.opensagres.nosql.ide.ui.editors.ModelFormEditor;

public class DocumentEditor extends
		ModelFormEditor<DocumentEditorInput, Document> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.editors.document.DocumentEditor";

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
