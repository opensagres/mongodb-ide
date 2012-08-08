package fr.opensagres.mongodb.ide.core.model.stats;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.Collection;

public class CollectionListStats extends ArrayList<CollectionStats> {

	private double totalSize;
	private double totalCount;

	public CollectionListStats(int size) {
		super(size);
		this.totalSize = 0;
	}

	@Override
	public boolean add(CollectionStats stats) {
		double size = stats.getSize();
		totalSize += size;
		double count = stats.getCount();
		totalCount += count;
		return super.add(stats);
	}

	public double getTotalSize() {
		return totalSize;
	}

	public void addCollection(Collection collection)
			throws UnknownHostException, MongoException {
		add(new CollectionStats(this, collection));
	}

	public double getTotalCount() {
		return totalCount;
	}

}
