package fr.opensagres.mongodb.ide.core.model.stats;

import java.net.UnknownHostException;

import com.mongodb.CommandResult;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.Collection;

public class CollectionStats {

	private final Collection collection;
	private double size;

	public CollectionStats(Collection collection) throws UnknownHostException,
			MongoException {
		this.collection = collection;
		CommandResult stats = collection.getShellCommandManager()
				.getDBCollectionGetStats(collection.getDBCollection());
		size = stats.getDouble("size");

		//System.err.println(stats);
	}

	public Collection getCollection() {
		return collection;
	}

	public double getSize() {
		return size;
	}
}
