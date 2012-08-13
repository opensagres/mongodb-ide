package fr.opensagres.mongodb.ide.core.model;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;

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

	@Override
	public NodeType getType() {
		return NodeType.GridFSBucket;
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
