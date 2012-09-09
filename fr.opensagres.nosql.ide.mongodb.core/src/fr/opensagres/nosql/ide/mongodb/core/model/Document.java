package fr.opensagres.nosql.ide.mongodb.core.model;

import java.util.List;

import com.mongodb.DBObject;

import fr.opensagres.nosql.ide.core.model.AbstractDocument;
import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeStatus;

public class Document extends AbstractDocument {

	private final DBObject dbObject;

	public Document(DBObject dbObject, ICollection collection) {
		super(collection);
		this.dbObject = dbObject;
	}

	public List<ITreeSimpleNode> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
