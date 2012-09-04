package fr.opensagres.nosql.ide.mongodb.core.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeSimpleNode;

public class Index extends TreeSimpleNode<IndexesCategory> {

	private final DBObject dbObject;
	private final String name;

	public Index(DBObject dbObject) {
		this.dbObject = dbObject;
		this.name = ((BasicDBObject) dbObject).getString("name");
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return getName();
	}

	public int getType() {
		return NodeTypeConstants.Index;
	}

}
