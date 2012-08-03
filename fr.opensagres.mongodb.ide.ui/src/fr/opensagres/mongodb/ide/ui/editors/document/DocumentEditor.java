package fr.opensagres.mongodb.ide.ui.editors.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import com.mongodb.DBObject;

import fr.opensagres.mongodb.ide.ui.editors.ModelFormEditor;

public class DocumentEditor extends
		ModelFormEditor<DocumentEditorInput, DBObject> {

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
	protected DBObject onLoad(DocumentEditorInput input) throws Exception {
		return input.getModel();
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {
		
	}

}
