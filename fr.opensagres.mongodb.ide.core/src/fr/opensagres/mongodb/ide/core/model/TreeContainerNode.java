package fr.opensagres.mongodb.ide.core.model;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeContainerNode<Parent extends TreeContainerNode, T extends TreeSimpleNode>
		extends TreeSimpleNode<Parent> {

	private NodeStatus status;

	private final List<T> children;

	public TreeContainerNode() {
		this.children = new ArrayList<T>();
		this.status = NodeStatus.Stopped;
	}

	public void addNode(T node) {
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
	
	public List<T> getChildren() {
		if (getStatus() != NodeStatus.Started) {
			clearNodes();
			try {
				this.doGetChildren();
				this.status = NodeStatus.Started;
			} catch (Throwable e) {
				this.status = NodeStatus.StartedWithError;
				this.addNode((T) new Error(e));
			}
		}
		return children;
	}

	public NodeStatus getStatus() {
		return status;
	}

	protected abstract void doGetChildren() throws Exception;
}
