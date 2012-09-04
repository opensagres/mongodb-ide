package fr.opensagres.nosql.ide.core.model;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.nosql.ide.core.internal.Messages;

public abstract class AbstractCollectionsCategory extends
		TreeContainerNode<IDatabase> implements ICollectionsCategory {

	protected final List<ICollection> collections;

	public AbstractCollectionsCategory() {
		this.collections = new ArrayList<ICollection>();
	}

	public String getName() {
		return Messages.Collections_label;
	}

	public String getLabel() {
		return getName();
	}

	public final IServer getServer() {
		return getParent().getServer();
	}

	public final int getType() {
		return NodeTypeConstants.CollectionsCategory;
	}

	public List<ICollection> getCollections() {
		// Compute if needed list of collection.
		getChildren();
		return collections;
	}

	@Override
	public void clearNodes() {
		super.clearNodes();
		this.collections.clear();
	}

	@Override
	protected void doGetChildren() throws Exception {
		this.collections.clear();
		loadCollections();
	}

	protected abstract void loadCollections() throws Exception;
}
