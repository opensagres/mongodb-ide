package fr.opensagres.mongodb.ide.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.misc.StatusUtil;

public class StackTraceErrorDialog {

	public static void openError(Shell parent, String dialogTitle,
			String title, Throwable e) {

		String message = "";
		Throwable nestedException = StatusUtil.getCause(e);
		IStatus status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
				message, nestedException);
		ErrorDialog.openError(parent, dialogTitle, message, status);

	}
}
