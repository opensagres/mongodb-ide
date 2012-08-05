package fr.opensagres.mongodb.ide.ui.editors.document;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class OverviewPage extends AbstractFormPage {

	public static final String ID = "overview";

	public OverviewPage(DocumentEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
	}

}
