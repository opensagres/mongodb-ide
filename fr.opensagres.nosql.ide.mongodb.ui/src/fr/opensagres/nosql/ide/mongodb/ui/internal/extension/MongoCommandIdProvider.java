package fr.opensagres.nosql.ide.mongodb.ui.internal.extension;

import fr.opensagres.nosql.ide.core.extensions.AbstractCommandIdProvider;
import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenCollectionEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenDatabaseEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenServerEditorHandler;

public class MongoCommandIdProvider extends AbstractCommandIdProvider {

	@Override
	protected String getCommmandId(int type, ITreeSimpleNode element) {
		switch (element.getType()) {
		case NodeTypeConstants.Server:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenServerEditorHandler.ID;
			}
		case NodeTypeConstants.Database:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenDatabaseEditorHandler.ID;
			}
		case NodeTypeConstants.Collection:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenCollectionEditorHandler.ID;
			}
		}
		return null;
	}
}
