package fr.opensagres.mongodb.ide.ui.editors.database;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelFormEditor;
import fr.opensagres.mongodb.ide.ui.editors.collection.IndexesPage;

public class DatabaseEditor extends
		BasicModelFormEditor<DatabaseEditorInput, Database> {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.database.DatabaseEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
		super.addPage(new StatsPage(this));
		super.addPage(new UsersPage(this));
	}

	@Override
	protected String getOverridePartName() {
		// modify the title of the editor with the name of the database.
		Database database = getModelObject();
		if (database != null) {
			return database.getName();
		}
		return null;
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
