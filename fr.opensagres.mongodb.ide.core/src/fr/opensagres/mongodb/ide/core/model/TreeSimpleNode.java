package fr.opensagres.mongodb.ide.core.model;

import com.mongodb.tools.shell.ShellCommandManager;

public abstract class TreeSimpleNode<Parent extends TreeContainerNode<?>>
		implements IModelIdentity {

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

	public abstract NodeType getType();

	public ShellCommandManager getShellCommandManager() {
		return getParent().getShellCommandManager();
	}

	public String getId() {
		return computeId();
	}
}
