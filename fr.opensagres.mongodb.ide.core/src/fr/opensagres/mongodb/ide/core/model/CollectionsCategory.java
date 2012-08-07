package fr.opensagres.mongodb.ide.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class CollectionsCategory extends TreeContainerNode<Database> {

	private String id;
	private final List<Collection> collections;

	public CollectionsCategory() {
		this.collections = new ArrayList<Collection>();
	}

	@Override
	protected void doGetChildren() throws Exception {
		this.collections.clear();
		DB db = getParent().getDB();
		Set<String> names = getShellCommandManager().showCollections(db);
		for (String name : names) {
			Collection collection = new Collection(name);
			collections.add(collection);
			super.addNode(collection);
		}
	}

	public String getId() {
		if (id == null) {
			this.id = computeId();
		}
		return id;
	}

	public String getName() {
		return Messages.Collections_label;
	}

	public String getLabel() {
		return Messages.Collections_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.CollectionsCategory;
	}

	public List<Collection> getCollections() {
		// Compute if needed list of collection.
		getChildren();
		return collections;
	}

	@Override
	public void clearNodes() {
		super.clearNodes();
		this.collections.clear();
	}

}
