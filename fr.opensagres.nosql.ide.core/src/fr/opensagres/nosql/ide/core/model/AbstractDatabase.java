package fr.opensagres.nosql.ide.core.model;

public abstract class AbstractDatabase extends TreeContainerNode<IServer>
		implements IDatabase {

	private String name;

	public AbstractDatabase(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return getName();
	}

	public final IServer getServer() {
		return getParent();
	}

	public final int getType() {
		return NodeTypeConstants.Database;
	}

}
