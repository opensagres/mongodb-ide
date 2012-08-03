package fr.opensagres.mongodb.ide.ui.editors.database;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.mongodb.ide.ui.editors.AbstractFormPage;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class StaticsPage extends AbstractFormPage {

	public static final String ID = "statics";

	public StaticsPage(FormEditor editor) {
		super(editor, ID, Messages.StaticsPage_title);
	}

}
