package fr.opensagres.mongodb.ide.core.model.stats;

public class IndexStats {

	private final CollectionStats collectionStats;
	private final String id;
	private final Integer indexSize;

	public IndexStats(CollectionStats collectionStats, String id,
			Integer indexSize) {
		this.collectionStats = collectionStats;
		this.id = id;
		this.indexSize = indexSize;
	}

	public String getId() {
		return id;
	}

	public Integer getIndexSize() {
		return indexSize;
	}

	public Integer getPercentIndexSize() {
		Integer totalSize = collectionStats.getListStats().getTotalIndexSize();
		return CollectionStats.getPercent(indexSize, totalSize);
	}
}
