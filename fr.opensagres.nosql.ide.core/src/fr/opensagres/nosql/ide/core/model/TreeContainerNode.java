package fr.opensagres.nosql.ide.core.model;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeContainerNode<Parent extends ITreeContainerNode>
		extends TreeSimpleNode<Parent> implements ITreeContainerNode<Parent> {

	private NodeStatus status;

	private final List<ITreeSimpleNode> children;

	public TreeContainerNode() {
		this.children = new ArrayList<ITreeSimpleNode>();
		this.status = NodeStatus.Stopped;
	}

	public void addNode(ITreeSimpleNode node) {
		node.setParent(this);
		children.add(node);
	}

	public void clearNodes() {
		clearNodes(false);
	}

	public void clearNodes(boolean updateStatus) {
		children.clear();
		if (updateStatus) {
			this.status = NodeStatus.Stopped;
		}
	}

	public List<ITreeSimpleNode> getChildren() {
		if (getStatus() != NodeStatus.Started) {
			clearNodes();
			try {
				this.doGetChildren();
				this.status = NodeStatus.Started;
			} catch (Throwable e) {
				this.status = NodeStatus.StartedWithError;
				this.addNode(new Error(e));
			}
		}
		return children;
	}

	public NodeStatus getStatus() {
		return status;
	}

	protected abstract void doGetChildren() throws Exception;
}
