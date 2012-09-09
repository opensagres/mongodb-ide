package fr.opensagres.nosql.ide.mongodb.core.model.stats;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import com.mongodb.tools.driver.StatsHelper;

import fr.opensagres.nosql.ide.mongodb.core.model.Collection;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

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
	private Integer count;
	private Integer size;
	private Integer storage;
	private Integer totalIndexSize;
	private Integer avgObj;
	private Integer padding;

	public CollectionStats(CollectionListStats listStats, Collection collection)
			throws UnknownHostException, MongoException {
		this.totalIndexSize = 0;
		this.listStats = listStats;
		this.collection = collection;
		CommandResult stats = MongoShellCommandManager.getInstance()
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

	public Integer getCount() {
		return count;
	}

	public Integer getPercentCount() {
		Integer totalCount = listStats.getTotalCount();
		return getPercent(count, totalCount);
	}

	public Integer getSize() {
		return size;
	}

	public Integer getPercentSize() {
		Integer totalSize = listStats.getTotalSize();
		return getPercent(size, totalSize);
	}

	public Integer getStorage() {
		return storage;
	}

	public Integer getPercentStorage() {
		Integer totalStorage = listStats.getTotalStorage();
		return getPercent(storage, totalStorage);
	}

	public Integer getAvgObj() {
		return avgObj;
	}

	public Integer getPercentAvgObj() {
		Integer totalAvgObj = listStats.getTotalAvgObj();
		return getPercent(avgObj, totalAvgObj);
	}

	public Integer getPadding() {
		return padding;
	}

	public Integer getPercentPadding() {
		Integer totalPadding = listStats.getTotalPadding();
		return getPercent(padding, totalPadding);
	}

	@Override
	public boolean add(IndexStats stats) {
		Integer size = stats.getIndexSize();
		totalIndexSize = add(totalIndexSize, size);
		return super.add(stats);
	}

	public Integer getTotalIndexSize() {
		return totalIndexSize;
	}

	public void addIndex(String id, Integer indexSize)
			throws UnknownHostException, MongoException {
		add(new IndexStats(this, id, indexSize));
	}

	public CollectionListStats getListStats() {
		return listStats;
	}

	public Integer getPercentIndexSize() {
		Integer totalSize = listStats.getTotalIndexSize();
		return getPercent(totalIndexSize, totalSize);
	}

	public static Integer getPercent(Integer value, Integer totalValue) {
		if (value == null || totalValue == null) {
			return null;
		}
		double d = ((double) value / (double) totalValue) * 100;
		return (int) Math.floor(d);
	}

	public static Integer add(Integer total, Integer value) {
		if (value == null) {
			return total;
		}
		if (total == null) {
			return value;
		}
		return total + value;
	}
}
