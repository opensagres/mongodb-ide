package fr.opensagres.nosql.ide.mongodb.core.model.stats;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.MongoException;

import fr.opensagres.nosql.ide.mongodb.core.model.Collection;

public class CollectionListStats extends ArrayList<CollectionStats> {

	private Integer totalSize;
	private Integer totalCount;
	private Integer totalStorage;
	private Integer totalAvgObj;
	private Integer totalPadding;
	private Integer totalIndexSize;

	public CollectionListStats(int size) {
		super(size);
		this.totalSize = 0;
		this.totalIndexSize = 0;
	}

	@Override
	public boolean add(CollectionStats stats) {
		totalSize = CollectionStats.add(totalSize, stats.getSize());
		totalCount = CollectionStats.add(totalCount, stats.getCount());
		totalStorage = CollectionStats.add(totalStorage, stats.getStorage());
		totalAvgObj = CollectionStats.add(totalAvgObj, stats.getAvgObj());
		totalPadding = CollectionStats.add(totalPadding, stats.getPadding());
		totalIndexSize = CollectionStats.add(totalIndexSize,
				stats.getTotalIndexSize());
		return super.add(stats);
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getTotalStorage() {
		return totalStorage;
	}

	public Integer getTotalAvgObj() {
		return totalAvgObj;
	}

	public Integer getTotalPadding() {
		return totalPadding;
	}

	public Integer getTotalIndexSize() {
		return totalIndexSize;
	}

	public void addCollection(Collection collection)
			throws UnknownHostException, MongoException {
		add(new CollectionStats(this, collection));
	}

}
