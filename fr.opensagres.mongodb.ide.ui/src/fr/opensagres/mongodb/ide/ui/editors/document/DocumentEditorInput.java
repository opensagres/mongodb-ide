package fr.opensagres.mongodb.ide.ui.editors.document;

import fr.opensagres.mongodb.ide.core.model.Document;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelEditorInput;

public class DocumentEditorInput extends BasicModelEditorInput<Document> {

	public DocumentEditorInput(Document document) {
		super(document);
	}

}
