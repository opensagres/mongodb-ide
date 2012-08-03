package fr.opensagres.mongodb.ide.core.model;

import com.mongodb.DBObject;

public class Document extends TreeSimpleNode<Collection> {

	private DBObject dbObject;

	public Document(DBObject dbObject) {
		this.dbObject = dbObject;
	}

	@Override
	public NodeType getType() {
		return NodeType.Document;
	}

	public String getLabel() {
		return dbObject.toString();
	}

	public String getId() {
		return null;
	}

	public String getName() {
		return "";
	}
}
