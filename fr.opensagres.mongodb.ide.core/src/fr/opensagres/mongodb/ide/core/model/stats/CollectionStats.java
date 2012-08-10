package fr.opensagres.mongodb.ide.core.model.stats;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.mongodb.ide.core.model.Collection;

public class CollectionStats extends ArrayList<IndexStats> {

	public static final String NAME_PROPERTY = "name";
	public static final String COUNT_PROPERTY = "count";
	public static final String SIZE_PROPERTY = "size";
	public static final String STORAGE_PROPERTY = "storage";
	public static final String TOTAL_INDEX_SIZE_PROPERTY = "totalIndexSize";
	public static final String AVGOBJ_PROPERTY = "avgObj";
	public static final String PADDING_PROPERTY = "padding";

	private final CollectionListStats listStats;
	private final Collection collection;
	private double count;
	private double size;
	private double storage;
	private double totalIndexSize;
	private double avgObj;
	private double padding;

	public CollectionStats(CollectionListStats listStats, Collection collection)
			throws UnknownHostException, MongoException {
		this.totalIndexSize = 0;
		this.listStats = listStats;
		this.collection = collection;
		CommandResult stats = collection.getShellCommandManager()
				.getDBCollectionGetStats(collection.getDBCollection());
		// Collection stats
		this.size = StatsHelper.getSize(stats);
		this.count = StatsHelper.getCount(stats);
		this.storage = StatsHelper.getStorage(stats);
		this.avgObj = StatsHelper.getAvgObj(stats);
		this.padding = StatsHelper.getPadding(stats);
		// Indexes stats
		BasicDBObject indexes = StatsHelper.getIndexSizes(stats);
		Set<String> indexNames = indexes.keySet();
		for (String id : indexNames) {
			addIndex(id, indexes.getInt(id));
		}
	}

	public Collection getCollection() {
		return collection;
	}

	public String getName() {
		return collection.getName();
	}

	public double getCount() {
		return count;
	}

	public double getPercentCount() {
		double totalCount = listStats.getTotalCount();
		if (count == 0) {
			return 0;
		}
		return (count / totalCount) * 100;
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

	public double getStorage() {
		return storage;
	}

	public double getPercentStorage() {
		double totalStorage = listStats.getTotalStorage();
		if (storage == 0) {
			return 0;
		}
		return (storage / totalStorage) * 100;
	}

	public double getAvgObj() {
		return avgObj;
	}

	public double getPercentAvgObj() {
		double totalAvgObj = listStats.getTotalAvgObj();
		if (avgObj == 0) {
			return 0;
		}
		return (avgObj / totalAvgObj) * 100;
	}

	public double getPadding() {
		return padding;
	}

	public double getPercentPadding() {
		double totalPadding = listStats.getTotalPadding();
		if (padding == 0) {
			return 0;
		}
		return (padding / totalPadding) * 100;
	}

	@Override
	public boolean add(IndexStats stats) {
		double size = stats.getIndexSize();
		totalIndexSize += size;
		return super.add(stats);
	}

	public double getTotalIndexSize() {
		return totalIndexSize;
	}

	public void addIndex(String id, double indexSize)
			throws UnknownHostException, MongoException {
		add(new IndexStats(this, id, indexSize));
	}

	public CollectionListStats getListStats() {
		return listStats;
	}

	public double getPercentIndexSize() {
		double totalSize = listStats.getTotalIndexSize();
		return (totalIndexSize / totalSize) * 100;
	}
}
