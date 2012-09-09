package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document;

import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.editors.AbstractFormPage;

public class JSONTextPage extends AbstractFormPage {

	public static final String ID = "jsonText";

	public JSONTextPage(DocumentEditor editor) {
		super(editor, ID, Messages.JSONTextPage_title);
	}

}
