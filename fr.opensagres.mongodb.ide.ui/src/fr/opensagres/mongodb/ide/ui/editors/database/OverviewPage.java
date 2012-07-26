package fr.opensagres.mongodb.ide.ui.editors.database;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class OverviewPage extends AbstractFormPage {

	public static final String ID = "overview";

	public OverviewPage(FormEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
	}

}