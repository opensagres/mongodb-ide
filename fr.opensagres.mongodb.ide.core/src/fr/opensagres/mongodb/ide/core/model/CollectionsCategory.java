package fr.opensagres.mongodb.ide.core.model;

import java.util.Set;

import com.mongodb.DB;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class CollectionsCategory extends TreeContainerNode<Database> {

	private String id;

	public CollectionsCategory() {

	}

	@Override
	protected void doGetChildren() throws Exception {
		DB db = getParent().getDB();
		Set<String> names = getShellCommandManager().showCollections(db);
		for (String name : names) {
			Collection collection = new Collection(name);
			super.addNode(collection);
		}
	}

	@Override
	public String getId() {
		if (id == null) {
			this.id = computeId();
		}
		return id;
	}

	@Override
	public String getName() {
		return Messages.Collections_label;
	}

	@Override
	public String getLabel() {
		return Messages.Collections_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.CollectionsCategory;
	}

}
