package fr.opensagres.nosql.ide.ui;

import org.eclipse.osgi.util.NLS;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.internal.Messages;

public class Validator {

	public static boolean validateServer(IServer server,
			IErrorMessageAware message) {
		// Server is required
		if (server == null) {
			message.setErrorMessage(Messages.server_validation_required);
			return false;
		}
		// Server must be connected
		if (!server.isConnected()) {
			message.setErrorMessage(NLS.bind(
					Messages.server_validation_notConnected, server.getName()));
			return false;
		}
		return true;
	}

	public static boolean validateDatatabaseName(String name,
			IErrorMessageAware message) {
		if (StringUtils.isEmpty(name)) {
			message.setErrorMessage(Messages.database_validation_required);
			return false;
		}
		return true;
	}

	public static boolean validateCollectionName(String name,
			IErrorMessageAware message) {
		if (StringUtils.isEmpty(name)) {
			message.setErrorMessage(Messages.collection_validation_required);
			return false;
		}
		return true;
	}
}
