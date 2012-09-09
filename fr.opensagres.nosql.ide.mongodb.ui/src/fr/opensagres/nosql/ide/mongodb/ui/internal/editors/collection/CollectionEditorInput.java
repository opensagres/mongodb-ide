package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.collection;

import fr.opensagres.nosql.ide.mongodb.core.model.Collection;
import fr.opensagres.nosql.ide.ui.editors.BasicModelEditorInput;

public class CollectionEditorInput extends BasicModelEditorInput<Collection> {

	public CollectionEditorInput(Collection collection) {
		super(collection);
	}
}
