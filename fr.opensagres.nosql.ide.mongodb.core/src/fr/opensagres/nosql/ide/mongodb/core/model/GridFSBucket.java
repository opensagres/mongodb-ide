package fr.opensagres.nosql.ide.mongodb.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;

import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeSimpleNode;

public class GridFSBucket extends TreeSimpleNode<GridFSCategory> {

	private GridFS gridFS;
	private String bucketName;

	public GridFSBucket(String bucketName) {
		this.bucketName = bucketName;
		this.gridFS = null;
	}

	public String getName() {
		return bucketName;
	}

	public String getLabel() {
		return getName();
	}

	public int getType() {
		return NodeTypeConstants.GridFSBucket;
	}

	public GridFS getDBGridFS() throws UnknownHostException, MongoException {
		if (gridFS != null) {
			return gridFS;
		}
		DB db = getParent().getParent().getDB();
		gridFS = new GridFS(db, bucketName);
		return gridFS;
	}

}
