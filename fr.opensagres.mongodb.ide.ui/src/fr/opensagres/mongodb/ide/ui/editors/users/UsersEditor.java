package fr.opensagres.mongodb.ide.ui.editors.users;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import com.mongodb.DBObject;

import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelFormEditor;

public class UsersEditor extends BasicModelFormEditor<UsersEditorInput, Users> {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.editors.users.UsersEditor";

	@Override
	protected void doAddPages() throws PartInitException {
		super.addPage(new OverviewPage(this));
		super.addPage(new UsersPage(this));
	}

	@Override
	protected String getActivePageIdOnLoad() {
		return UsersPage.ID;
	}

	public List<DBObject> getDBUsers() throws Exception {
		return getModelObject().getParent().getUsers();
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {

	}

}
