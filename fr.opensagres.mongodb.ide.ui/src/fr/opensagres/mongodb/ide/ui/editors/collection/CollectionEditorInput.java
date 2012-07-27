package fr.opensagres.mongodb.ide.ui.editors.collection;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.ui.editors.AbstractEditorInput;

public class CollectionEditorInput extends AbstractEditorInput {

	private final Collection collection;

	public CollectionEditorInput(Collection collection) {
		this.collection = collection;
	}

	public Collection getCollection() {
		return collection;
	}

	public String getName() {
		return getCollection().getName();
	}

	public String getToolTipText() {
		return getCollection().getLabel();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CollectionEditorInput))
			return false;
		CollectionEditorInput other = (CollectionEditorInput) obj;
		if (collection == null) {
			if (other.getCollection() != null) {
				return false;
			}
			return true;
		}
		String collectionId = collection.getId();
		if (collectionId == null) {
			if (other.getCollection().getId() != null)
				return false;
		} else if (!collectionId.equals(other.getCollection().getId()))
			return false;
		return true;
	}

}
