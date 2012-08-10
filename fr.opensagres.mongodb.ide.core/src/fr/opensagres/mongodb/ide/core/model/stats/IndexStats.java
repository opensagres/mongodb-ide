package fr.opensagres.mongodb.ide.core.model.stats;

public class IndexStats {

	private final CollectionStats collectionStats;
	private final String id;
	private final double indexSize;

	public IndexStats(CollectionStats collectionStats, String id, double indexSize) {
		this.collectionStats = collectionStats;
		this.id = id;
		this.indexSize = indexSize;
	}

	public String getId() {
		return id;
	}

	public double getIndexSize() {
		return indexSize;
	}

	public double getPercentIndexSize() {
		double totalSize = collectionStats.getListStats().getTotalIndexSize();
		if (indexSize == 0) {
			return 0;
		}
		return (indexSize / totalSize) * 100;
	}
}
