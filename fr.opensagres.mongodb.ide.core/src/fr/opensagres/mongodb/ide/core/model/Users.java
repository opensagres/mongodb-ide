package fr.opensagres.mongodb.ide.core.model;

import fr.opensagres.mongodb.ide.core.internal.Messages;

public class Users extends TreeSimpleNode<Database> {

	private String id;

	@Override
	public String getId() {
		if (id == null) {
			this.id = computeId();
		}
		return id;
	}

	@Override
	public String getName() {
		return Messages.Users_label;
	}

	@Override
	public String getLabel() {
		return Messages.Users_label;
	}

	@Override
	public NodeType getType() {
		return NodeType.Users;
	}

}
