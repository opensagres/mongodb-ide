package fr.opensagres.nosql.ide.core.model;

public interface ITreeSimpleNode<Parent extends ITreeContainerNode> extends
		IModelIdentity {

	Parent getParent();

	void setParent(Parent parent);

	int getType();
}
