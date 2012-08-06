package fr.opensagres.mongodb.ide.core.model;

import com.mongodb.DBObject;
import com.mongodb.tools.driver.DBObjectHelper;

public class Document extends TreeSimpleNode<Collection> {

	private DBObject dbObject;

	public Document(DBObject dbObject, Collection collection) {
		this.dbObject = dbObject;
		super.setParent(collection);
	}

	@Override
	public NodeType getType() {
		return NodeType.Document;
	}

	public String getLabel() {
		return getId();
	}

	public String getId() {
		return "TODO"; //DBObjectHelper.getId(dbObject);
	}

	public String getName() {
		return getId();
	}

}
