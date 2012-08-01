package fr.opensagres.mongodb.ide.core.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Index extends TreeSimpleNode<IndexesCategory> {

	private final DBObject dbObject;
	private final String name;
	private String id;

	public Index(DBObject dbObject) {
		this.dbObject = dbObject;
		this.name = ((BasicDBObject) dbObject).getString("name");
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
		return name;
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public NodeType getType() {
		return NodeType.Index;
	}

}
