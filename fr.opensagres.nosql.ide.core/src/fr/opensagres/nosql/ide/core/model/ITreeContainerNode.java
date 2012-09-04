package fr.opensagres.nosql.ide.core.model;

import java.util.List;

public interface ITreeContainerNode<Parent extends ITreeContainerNode> extends
		ITreeSimpleNode<Parent> {

	List<ITreeSimpleNode> getChildren();

	NodeStatus getStatus();

}
