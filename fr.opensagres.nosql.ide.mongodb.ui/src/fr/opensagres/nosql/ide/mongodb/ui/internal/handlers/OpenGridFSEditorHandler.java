package fr.opensagres.nosql.ide.mongodb.ui.internal.handlers;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.mongodb.core.model.GridFSBucket;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.gridfs.GridFSEditor;
import fr.opensagres.nosql.ide.mongodb.ui.internal.editors.gridfs.GridFSEditorInput;
import fr.opensagres.nosql.ide.ui.handlers.OpenEditorHandler;

public class OpenGridFSEditorHandler extends OpenEditorHandler<GridFSBucket> {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.handlers.OpenGridFSEditorHandler";

	@Override
	protected String getEditorId() {
		return GridFSEditor.ID;
	}

	@Override
	protected IEditorInput createEditorInput(GridFSBucket server) {
		return new GridFSEditorInput(server);
	}

}
