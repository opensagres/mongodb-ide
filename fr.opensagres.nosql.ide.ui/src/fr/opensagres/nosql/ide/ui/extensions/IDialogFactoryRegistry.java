package fr.opensagres.nosql.ide.ui.extensions;

import fr.opensagres.nosql.ide.core.extensions.IServerType;

public interface IDialogFactoryRegistry {

	IDialogFactory getFactory(IServerType serverType, String dialogType);

}
