package fr.opensagres.mongodb.ide.ui.editors.document;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class JSONTextPage extends AbstractFormPage {

	public static final String ID = "jsonText";

	public JSONTextPage(DocumentEditor editor) {
		super(editor, ID, Messages.JSONTextPage_title);
	}

}
