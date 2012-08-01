package fr.opensagres.mongodb.ide.core.model;

public abstract class TreeSimpleNode<Parent extends TreeContainerNode<?>> {

	@SuppressWarnings("rawtypes")
	public static final TreeSimpleNode[] EMPTY = new TreeSimpleNode[0];

	protected static final String SEPARATOR = "____";

	private Parent parent;

	public Parent getParent() {
		return parent;
	}

	protected void setParent(Parent parent) {
		this.parent = parent;
	}

	protected String computeId() {
		return getParent().getId() + SEPARATOR + getName();
	}

	public abstract String getId();
	
	public abstract String getName();

	public abstract String getLabel();

	public abstract NodeType getType();
}
