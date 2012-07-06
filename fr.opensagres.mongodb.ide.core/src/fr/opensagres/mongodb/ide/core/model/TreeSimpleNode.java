package fr.opensagres.mongodb.ide.core.model;

public abstract class TreeSimpleNode<Parent extends TreeContainerNode<?, ?>> {

	@SuppressWarnings("rawtypes")
	public static final TreeSimpleNode[] EMPTY = new TreeSimpleNode[0];

	private Parent parent;

	public Parent getParent() {
		return parent;
	}
	
	protected void setParent(Parent parent) {
		this.parent = parent;
	}

	public abstract String getLabel();

	public abstract NodeType getType();
}
