package fr.opensagres.mongodb.ide.core.model.stats;

import java.net.UnknownHostException;

import com.mongodb.CommandResult;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.Collection;

public class CollectionStats {

	private final CollectionListStats listStats;
	private final Collection collection;
	private double size;
	private double count;

	public CollectionStats(CollectionListStats listStats, Collection collection)
			throws UnknownHostException, MongoException {
		this.listStats = listStats;
		this.collection = collection;
		CommandResult stats = collection.getShellCommandManager()
				.getDBCollectionGetStats(collection.getDBCollection());
		size = stats.getDouble("size");
		count = stats.getDouble("count");

		// System.err.println(stats);
	}

	public Collection getCollection() {
		return collection;
	}

	public double getSize() {
		return size;
	}

	public double getPercentSize() {
		double totalSize = listStats.getTotalSize();
		if (size == 0) {
			return 0;
		}
		return (size / totalSize) * 100;
	}

	public double getCount() {
		return count;
	}

	public double getPercentCount() {
		double totalCount = listStats.getTotalCount();
		if (count == 0) {
			return 0;
		}
		return (size / totalCount) * 100;
	}
}
