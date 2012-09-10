package fr.opensagres.nosql.ide.orientdb.ui.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.orientdb.ui.internal.handlers.NewServerWizardHandler;
import fr.opensagres.nosql.ide.ui.extensions.AbstractCommandIdProvider;

public class OrientCommandIdProvider extends AbstractCommandIdProvider {

	@Override
	protected String getServerTypeCommandId(int type, IServerType element) {
		switch (type) {
		case ICommandIdProvider.OPEN_NEW_WIZARD:
			return NewServerWizardHandler.ID;
		}
		return null;
	}

	@Override
	protected String getCommmandId(int type, ITreeSimpleNode element) {
		return null;
	}
}
