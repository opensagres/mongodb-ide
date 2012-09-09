package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.gridfs;

import fr.opensagres.nosql.ide.mongodb.core.model.GridFSBucket;
import fr.opensagres.nosql.ide.ui.editors.BasicModelEditorInput;

public class GridFSEditorInput extends BasicModelEditorInput<GridFSBucket> {

	public GridFSEditorInput(GridFSBucket bucket) {
		super(bucket);
	}
}
