package fr.opensagres.nosql.ide.core.model;

import fr.opensagres.nosql.ide.core.internal.Messages;

public class Users extends TreeSimpleNode<IDatabase> {

	public String getName() {
		return Messages.Users_label;
	}

	public String getLabel() {
		return Messages.Users_label;
	}

	public int getType() {
		return NodeTypeConstants.Users;
	}

}
