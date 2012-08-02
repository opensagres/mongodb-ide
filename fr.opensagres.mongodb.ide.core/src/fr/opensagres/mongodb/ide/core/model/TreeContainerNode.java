package fr.opensagres.mongodb.ide.core.model;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.tools.shell.ShellCommandManager;

public abstract class TreeContainerNode<Parent extends TreeContainerNode>
		extends TreeSimpleNode<Parent> {

	private NodeStatus status;

	private final List<TreeSimpleNode> children;

	public TreeContainerNode() {
		this.children = new ArrayList<TreeSimpleNode>();
		this.status = NodeStatus.Stopped;
	}

	public void addNode(TreeSimpleNode node) {
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
	
	public List<TreeSimpleNode> getChildren() {
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
