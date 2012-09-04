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

}
