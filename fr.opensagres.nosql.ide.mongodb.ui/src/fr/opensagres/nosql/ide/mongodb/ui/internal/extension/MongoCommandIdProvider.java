package fr.opensagres.nosql.ide.mongodb.ui.internal.extension;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenCollectionEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenDatabaseEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenDocumentEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenGridFSEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenServerEditorHandler;
import fr.opensagres.nosql.ide.mongodb.ui.internal.handlers.OpenUsersEditorHandler;
import fr.opensagres.nosql.ide.ui.extensions.AbstractCommandIdProvider;

public class MongoCommandIdProvider extends AbstractCommandIdProvider {

	@Override
	protected String getCommmandId(int type, ITreeSimpleNode element) {
		switch (element.getType()) {
		case NodeTypeConstants.Server:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenServerEditorHandler.ID;
			}
			return null;
		case NodeTypeConstants.Database:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenDatabaseEditorHandler.ID;
			}
			return null;
		case NodeTypeConstants.Users:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenUsersEditorHandler.ID;
			}
			return null;
		case NodeTypeConstants.GridFSBucket:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenGridFSEditorHandler.ID;

			}
			return null;
		case NodeTypeConstants.Collection:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenCollectionEditorHandler.ID;
			}
			return null;
		case NodeTypeConstants.Document:
			switch (type) {
			case ICommandIdProvider.OPEN_EDITOR:
				return OpenDocumentEditorHandler.ID;
			}
			return null;
		}
		return null;
	}
}
