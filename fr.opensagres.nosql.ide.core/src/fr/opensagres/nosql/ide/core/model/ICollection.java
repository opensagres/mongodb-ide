package fr.opensagres.nosql.ide.core.model;

public interface ICollection extends ITreeContainerNode<ICollectionsCategory> {

	String getNameWithDB();

	IDatabase getDatabase();

}
