package fr.opensagres.nosql.ide.ui.extensions;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public interface IDialogFactory {

	Window create(Shell shell);

}
