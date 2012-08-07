package fr.opensagres.mongodb.ide.core.internal.extensions;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.environment.Constants;

public class CommandExecHelper {

	public static void exec(String command) throws IOException {
		String nativeCommand = null;
		boolean isWin9xME = false; // see bug 50567
		if (Platform.getOS().equals(Constants.OS_WIN32)) {
			String osName = System.getProperty("os.name"); //$NON-NLS-1$
			isWin9xME = osName != null
					&& (osName.startsWith("Windows 9") || osName.startsWith("Windows ME")); //$NON-NLS-1$ //$NON-NLS-2$
			if (isWin9xME) {
				nativeCommand = "command.com /C start "; //$NON-NLS-1$
			} else {
				// Win NT, 2K, XP
				nativeCommand = "cmd.exe /C  start "; //$NON-NLS-1$
			}
		} else if (!Platform.getOS().equals(Constants.OS_UNKNOWN)) {
			//nativeCommand = "env"; //$NON-NLS-1$
			// TODO : manage linux shell command etc
		}
		if (nativeCommand == null) {
			return;
		}
		nativeCommand = nativeCommand + command; //$NON-NLS-1$
		Runtime.getRuntime().exec(nativeCommand);
	}
}
