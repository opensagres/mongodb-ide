package fr.opensagres.nosql.ide.mongodb.ui.internal.extension;

import fr.opensagres.nosql.ide.core.extensions.AbstractCommandIdProvider;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenServerEditorHandler;

public class MongoCommandIdProvider extends AbstractCommandIdProvider {

	@Override
	protected String getCommmandId(ITreeSimpleNode element) {
		switch (element.getType()) {
		case NodeTypeConstants.Server:
			return OpenServerEditorHandler.ID;
		}
		return null;
	}
}
