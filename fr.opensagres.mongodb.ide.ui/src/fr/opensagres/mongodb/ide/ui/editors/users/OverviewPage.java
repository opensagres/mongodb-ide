package fr.opensagres.mongodb.ide.ui.editors.users;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class OverviewPage extends AbstractToolbarFormPage {

	public static final String ID = "overview";

	public OverviewPage(UsersEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {

		// http://www.mongodb.org/display/DOCS/Security+and+Authentication#SecurityandAuthentication-Configuring
	}

}
