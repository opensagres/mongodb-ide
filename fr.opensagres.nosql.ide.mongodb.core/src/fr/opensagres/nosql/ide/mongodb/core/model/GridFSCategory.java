package fr.opensagres.nosql.ide.mongodb.core.model;

import java.util.Set;

import com.mongodb.tools.driver.GridFSHelper;

import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeContainerNode;
import fr.opensagres.nosql.ide.mongodb.core.internal.Messages;

public class GridFSCategory extends TreeContainerNode<Database> {

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

	public String getName() {
		return Messages.GridFS_label;
	}

	public String getLabel() {
		return Messages.GridFS_label;
	}

	public int getType() {
		return NodeTypeConstants.GridFSCategory;
	}

}
