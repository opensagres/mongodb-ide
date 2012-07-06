package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class Collection extends TreeContainerNode<Database, Document> {

	private String name;
	private DBCollection dbCollection;

	public Collection(String name) {
		this.name = name;
		this.dbCollection = null;
	}

	@Override
	public NodeType getType() {
		return NodeType.Collection;
	}

	@Override
	public String getLabel() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void doGetChildren() throws Exception {
		DBCollection dbCollection = getDBCollection();
		DBCursor cur = dbCollection.find();
		while(cur.hasNext()) {
			DBObject object = cur.next();
			super.addNode(new Document(object));
		}
	}

	public DBCollection getDBCollection() throws UnknownHostException,
			MongoException {
		if (dbCollection == null) {
			DB db = getParent().getDB();
			dbCollection = db.getCollection(getName());
		}
		return dbCollection;
	}
}
