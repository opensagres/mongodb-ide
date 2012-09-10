package fr.opensagres.nosql.ide.core.model;

public abstract class AbstractDocument extends TreeSimpleNode<ICollection>
		implements IDocument {

	public AbstractDocument(ICollection collection) {
		super.setParent(collection);
	}

	public int getType() {
		return NodeTypeConstants.Document;
	}

	@Override
	public String getId() {
		return "Document";
	}

	public String getLabel() {
		return getId();
	}

	public String getName() {
		return getId();
	}

}
