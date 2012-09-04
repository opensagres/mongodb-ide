package fr.opensagres.nosql.ide.mongodb.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;

import fr.opensagres.nosql.ide.core.model.AbstractCollection;

public class Collection extends AbstractCollection {

	public Collection(String name) {
		super(name);
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
		DB db = ((Database) getParent().getParent()).getDB();
		return db.getCollection(getName());
	}

}
