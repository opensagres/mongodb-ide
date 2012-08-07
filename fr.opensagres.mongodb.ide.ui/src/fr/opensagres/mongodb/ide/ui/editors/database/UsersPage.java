package fr.opensagres.mongodb.ide.ui.editors.database;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.ui.editors.AbstractMasterDetailsBlock;
import fr.opensagres.mongodb.ide.ui.editors.AbstractMasterDetailsFormPage;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

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
