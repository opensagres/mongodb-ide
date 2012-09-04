package fr.opensagres.nosql.ide.core.model;

public interface ITreeSimpleNode<Parent extends ITreeContainerNode> extends
		IModelIdentity {

	IServer getServer();

	Parent getParent();

	void setParent(Parent parent);

	int getType();
}
