package fr.opensagres.mongodb.ide.ui.editors.collection;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.ui.editors.BasicModelEditorInput;

public class CollectionEditorInput extends BasicModelEditorInput<Collection> {

	public CollectionEditorInput(Collection collection) {
		super(collection);
	}
}
