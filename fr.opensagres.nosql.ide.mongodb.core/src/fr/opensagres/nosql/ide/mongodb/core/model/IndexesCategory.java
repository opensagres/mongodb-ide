package fr.opensagres.nosql.ide.mongodb.core.model;

import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeContainerNode;
import fr.opensagres.nosql.ide.mongodb.core.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

public class IndexesCategory extends TreeContainerNode<Collection> {

	@Override
	protected void doGetChildren() throws Exception {
		DBCollection dbCollection = getParent().getDBCollection();
		List<DBObject> infos = MongoShellCommandManager.getInstance()
				.getDBCollectionGetIndexes(getServer(), dbCollection);
		for (DBObject dbObject : infos) {
			super.addNode(new Index(dbObject));
		}
	}

	public String getName() {
		return Messages.Indexes_label;
	}

	public String getLabel() {
		return Messages.Indexes_label;
	}

	public int getType() {
		return NodeTypeConstants.IndexesCategory;
	}

}
