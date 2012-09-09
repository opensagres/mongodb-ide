package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document;

import fr.opensagres.nosql.ide.mongodb.core.model.Document;
import fr.opensagres.nosql.ide.ui.editors.BasicModelEditorInput;

public class DocumentEditorInput extends BasicModelEditorInput<Document> {

	public DocumentEditorInput(Document document) {
		super(document);
	}

}
