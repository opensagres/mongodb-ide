package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.mongodb.core.model.Collection;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.collection.CollectionEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.collection.CollectionEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenCollectionEditorHandler extends OpenEditorHandler<Collection> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenCollectionEditorHandler";

	@Override
	protected String getEditorId() {
		return CollectionEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(Collection server) {
		return new CollectionEditorInput(server);
	}

}
