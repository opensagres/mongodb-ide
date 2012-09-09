package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.editors.AbstractMasterDetailsBlock;
import fr.opensagres.nosql.ide.ui.editors.AbstractMasterDetailsFormPage;

// http://www.mongodb.org/display/DOCS/Security+and+Authentication#SecurityandAuthentication-Configuring
public class UsersPage extends AbstractMasterDetailsFormPage {

	public static final String ID = "users";

	public UsersPage(DatabaseEditor editor) {
		super(editor, ID, Messages.UsersPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_USERS_16);
	}

	@Override
	protected AbstractMasterDetailsBlock createMasterDetailsBlock() {
		return new UsersMasterDetailsBlock(this);
	}

}
