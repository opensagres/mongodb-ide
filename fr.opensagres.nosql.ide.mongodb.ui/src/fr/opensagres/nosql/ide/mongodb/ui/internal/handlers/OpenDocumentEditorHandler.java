package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.mongodb.core.model.Document;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document.DocumentEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.document.DocumentEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenDocumentEditorHandler extends OpenEditorHandler<Document> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenDocumentEditorHandler";

	@Override
	protected String getEditorId() {
		return DocumentEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(Document server) {
		return new DocumentEditorInput(server);
	}

}