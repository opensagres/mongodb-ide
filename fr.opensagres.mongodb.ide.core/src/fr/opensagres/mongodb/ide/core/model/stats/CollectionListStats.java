package fr.opensagres.mongodb.ide.core.model.stats;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.model.Collection;

public class CollectionListStats extends ArrayList<CollectionStats> {

	private double totalSize;
	private double totalCount;
	private double totalStorage;
	private double totalAvgObj;
	private double totalPadding;
	private double totalIndexSize;

	public CollectionListStats(int size) {
		super(size);
		this.totalSize = 0;
		this.totalIndexSize = 0;
	}

	@Override
	public boolean add(CollectionStats stats) {
		totalSize += stats.getSize();
		totalCount += stats.getCount();
		totalStorage += stats.getStorage();
		totalAvgObj += stats.getAvgObj();
		totalPadding += stats.getPadding();
		totalIndexSize += stats.getTotalIndexSize();
		return super.add(stats);
	}

	public double getTotalSize() {
		return totalSize;
	}

	public double getTotalCount() {
		return totalCount;
	}

	public double getTotalStorage() {
		return totalStorage;
	}

	public double getTotalAvgObj() {
		return totalAvgObj;
	}

	public double getTotalPadding() {
		return totalPadding;
	}

	public double getTotalIndexSize() {
		return totalIndexSize;
	}

	public void addCollection(Collection collection)
			throws UnknownHostException, MongoException {
		add(new CollectionStats(this, collection));
	}

}
