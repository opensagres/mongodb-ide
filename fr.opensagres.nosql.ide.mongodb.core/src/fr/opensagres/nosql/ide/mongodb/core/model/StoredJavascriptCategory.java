package fr.opensagres.nosql.ide.mongodb.core.model;

import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.model.TreeContainerNode;
import fr.opensagres.nosql.ide.mongodb.core.internal.Messages;

public class StoredJavascriptCategory extends TreeContainerNode<Database> {

	public StoredJavascriptCategory() {

	}

	@Override
	protected void doGetChildren() throws Exception {

	}

	public String getName() {
		return Messages.StoredJavascript_label;
	}

	public String getLabel() {
		return Messages.StoredJavascript_label;
	}

	public int getType() {
		return NodeTypeConstants.StoredJavascriptCategory;
	}

}
