package fr.opensagres.mongodb.ide.core.model;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class StoredJavascriptCategory extends TreeContainerNode<Database> {

	private String id;

	public StoredJavascriptCategory() {

	}

	@Override
	protected void doGetChildren() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getId() {
		if (id == null) {
			this.id = computeId();
		}
		return id;
	}

	public String getName() {
		return Messages.StoredJavascript_label;
	}

	public String getLabel() {
		return Messages.StoredJavascript_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.StoredJavascriptCategory;
	}

}
