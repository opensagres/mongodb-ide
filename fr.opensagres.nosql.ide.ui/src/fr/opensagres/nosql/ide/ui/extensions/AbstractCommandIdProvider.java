package fr.opensagres.nosql.ide.ui.extensions;

import fr.opensagres.nosql.ide.core.extensions.ICommandIdProvider;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.handlers.wizards.database.NewDatabaseWizardHandler;

public abstract class AbstractCommandIdProvider implements ICommandIdProvider {

	public String getCommmandId(int type, Object element) {
		if (element instanceof ITreeSimpleNode) {
			String commandId = getCommmandId(type, (ITreeSimpleNode) element);
			if (StringUtils.isNotEmpty(commandId)) {
				return commandId;
			}
			return getGenericCommandId(type, (ITreeSimpleNode) element);
		}
		return null;
	}

	private String getGenericCommandId(int type, ITreeSimpleNode element) {
		switch (element.getType()) {
		case NodeTypeConstants.Server:
			switch(type) {
			case OPEN_NEW_WIZARD:
				return NewDatabaseWizardHandler.ID;
			}
		}
		return null;
	}

	protected abstract String getCommmandId(int type, ITreeSimpleNode element);
}
