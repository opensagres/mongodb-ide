package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoException;

public class Database extends TreeContainerNode<Server, Collection> {

	private String name;
	private DB db;

	public Database(String name) {
		super();
		setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public NodeType getType() {
		return NodeType.Database;
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	protected void doGetChildren() throws Exception {
		DB db = getDB();
		Set<String> names = db.getCollectionNames();
		for (String name : names) {
			Collection collection = new Collection(name);
			super.addNode(collection);
		}
	}
	
	public DB getDB() throws UnknownHostException, MongoException {
		if (db == null) {
			Server server = getParent();
			db = server.getMongo().getDB(getName());
		}
		return db;
		
	}
}
