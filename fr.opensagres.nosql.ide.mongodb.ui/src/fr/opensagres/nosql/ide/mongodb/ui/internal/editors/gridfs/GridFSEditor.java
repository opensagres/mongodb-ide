package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.gridfs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.nosql.ide.mongodb.core.model.GridFSBucket;
import fr.opensagres.nosql.ide.ui.editors.BasicModelFormEditor;

public class GridFSEditor extends
		BasicModelFormEditor<GridFSEditorInput, GridFSBucket> {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.gridfs.GridFSEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
		super.addPage(new FilesPage(this));		
	}

	@Override
	protected String getOverridePartName() {
		// modify the title of the editor with the name of the gridfs bucket.
		GridFSBucket database = getModelObject();
		if (database != null) {
			return database.getName();
		}
		return null;
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
