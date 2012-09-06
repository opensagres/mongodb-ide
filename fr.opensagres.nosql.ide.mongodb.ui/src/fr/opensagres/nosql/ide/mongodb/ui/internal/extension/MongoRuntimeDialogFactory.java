package fr.opensagres.nosql.ide.mongodb.ui.internal.extension;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.nosql.ide.mongodb.ui.dialogs.MongoRuntimeDialog;
import fr.opensagres.nosql.ide.ui.extensions.IDialogFactory;

public class MongoRuntimeDialogFactory implements IDialogFactory {

	public Window create(Shell shell) {
		return new MongoRuntimeDialog(shell);
	}
}
