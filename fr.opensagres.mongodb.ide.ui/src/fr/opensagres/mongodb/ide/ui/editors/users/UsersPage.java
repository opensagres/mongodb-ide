package fr.opensagres.mongodb.ide.ui.editors.users;

import org.eclipse.swt.graphics.Image;

import fr.opensagres.mongodb.ide.ui.editors.AbstractMasterDetailsBlock;
import fr.opensagres.mongodb.ide.ui.editors.AbstractMasterDetailsFormPage;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class UsersPage extends AbstractMasterDetailsFormPage {

	public static final String ID = "users";

	public UsersPage(UsersEditor editor) {
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
