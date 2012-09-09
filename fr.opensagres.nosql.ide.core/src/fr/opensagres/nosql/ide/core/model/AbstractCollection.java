package fr.opensagres.nosql.ide.core.model;

public abstract class AbstractCollection extends
		TreeContainerNode<ICollectionsCategory> implements ICollection {

	private String name;

	public AbstractCollection(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return getName();
	}

	public final int getType() {
		return NodeTypeConstants.Collection;
	}

	public String getNameWithDB() {
		StringBuilder name = new StringBuilder(getDatabase().getName());
		name.append("/");
		name.append(this.getName());
		return name.toString();
	}

	public IDatabase getDatabase() {
		return getParent().getParent();
	}
}
