package fr.opensagres.mongodb.ide.ui.editors.gridfs;

import fr.opensagres.mongodb.ide.core.model.GridFSBucket;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelEditorInput;

public class GridFSEditorInput extends BasicModelEditorInput<GridFSBucket> {

	public GridFSEditorInput(GridFSBucket bucket) {
		super(bucket);
	}
}
