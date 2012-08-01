package fr.opensagres.mongodb.ide.core.model;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class GridFSCategory extends TreeContainerNode<Database> {

	private String id;

	public GridFSCategory() {

	}

	@Override
	protected void doGetChildren() throws Exception {
		// TODO Auto-generated method stub

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
		return Messages.GridFS_label;
	}

	@Override
	public String getLabel() {
		return Messages.GridFS_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.GridFSCategory;
	}

}
