package fr.opensagres.mongodb.ide.ui.editors.database;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class StaticsPage extends AbstractFormPage {

	public static final String ID = "statics";

	public StaticsPage(DatabaseEditor editor) {
		super(editor, ID, Messages.StaticsPage_title);
	}

}
