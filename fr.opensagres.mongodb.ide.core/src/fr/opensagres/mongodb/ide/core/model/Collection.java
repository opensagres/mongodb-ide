package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;

public class Collection extends TreeContainerNode<CollectionsCategory> {

	private String id;
	private String name;
	private DBCollection dbCollection;

	public Collection(String name) {
		this.name = name;
		this.dbCollection = null;
	}

	@Override
	protected void setParent(CollectionsCategory parent) {
		super.setParent(parent);
		this.id = computeId();
	}

	@Override
	public NodeType getType() {
		return NodeType.Collection;
	}

	public String getLabel() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.id = computeId();
	}

	@Override
	protected void doGetChildren() throws Exception {
		// TODO display document with pagination in the tree
		// DBCollection dbCollection = getDBCollection();
		// DBCursor cur = dbCollection.find();
		// while (cur.hasNext()) {
		// DBObject object = cur.next();
		// super.addNode(new Document(object));
		// }
		super.addNode(new IndexesCategory());
	}

	public DBCollection getDBCollection() throws UnknownHostException,
			MongoException {
		if (dbCollection == null) {
			DB db = getParent().getParent().getDB();
			dbCollection = db.getCollection(getName());
		}
		return dbCollection;
	}

	public Database getDatabase() {
		return getParent().getParent();
	}

	public String getId() {
		return id;
	}

	public String getNameWithDB() {
		StringBuilder name = new StringBuilder(getDatabase().getName());
		name.append("/");
		name.append(this.getName());
		return name.toString();
	}

}
