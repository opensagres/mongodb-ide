package fr.opensagres.mongodb.ide.core.model;

import java.util.Set;

import com.mongodb.tools.driver.GridFSHelper;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class GridFSCategory extends TreeContainerNode<Database> {

	private String id;

	public GridFSCategory() {

	}

	@Override
	protected void doGetChildren() throws Exception {
		Set<String> names = GridFSHelper.getGridFSBucketNames(getParent()
				.getDB(), true);
		for (String bucketName : names) {
			super.addNode(new GridFSBucket(bucketName));
		}
	}

	public String getId() {
		if (id == null) {
			this.id = computeId();
		}
		return id;
	}

	public String getName() {
		return Messages.GridFS_label;
	}

	public String getLabel() {
		return Messages.GridFS_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.GridFSCategory;
	}

}
