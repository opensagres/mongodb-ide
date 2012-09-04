package fr.opensagres.nosql.ide.ui.internal.viewers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.nosql.ide.core.shell.IShellCommand;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;

public class ShellCommandLabelProvider extends LabelProvider {

	private static ShellCommandLabelProvider instance;

	public static ShellCommandLabelProvider getInstance() {
		synchronized (ShellCommandLabelProvider.class) {
			if (instance == null) {
				instance = new ShellCommandLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		return ((IShellCommand) element).getCommand();
	}

	@Override
	public Image getImage(Object element) {
		return ImageResources.getImage(ImageResources.IMG_COMMAND_16);
	}

}
