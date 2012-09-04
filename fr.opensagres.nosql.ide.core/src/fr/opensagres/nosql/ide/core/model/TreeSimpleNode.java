package fr.opensagres.nosql.ide.core.model;

public abstract class TreeSimpleNode<Parent extends ITreeContainerNode>
		implements ITreeSimpleNode<Parent> {

	@SuppressWarnings("rawtypes")
	public static final TreeSimpleNode[] EMPTY = new TreeSimpleNode[0];

	protected static final String SEPARATOR = "____";

	private Parent parent;

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	protected String computeId() {
		return getParent().getId() + SEPARATOR + getName();
	}

	public String getId() {
		return computeId();
	}

	public IServer getServer() {
		return getParent().getServer();
	}
}
