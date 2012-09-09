package fr.opensagres.nosql.ide.ui.internal.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IServiceLocator;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.nosql.ide.ui.handlers.ContextHandlerUtils;

public abstract class AbstractCommandAction extends AbstractTreeNodeAction {

	private final Shell parentShell;
	private final IServiceLocator serviceLocator;
	private final int commandType;

	protected AbstractCommandAction(int commandType,
			ISelectionProvider provider, String text,
			IServiceLocator serviceLocator, Shell parentShell) {
		super(provider, text);
		this.serviceLocator = serviceLocator;
		this.parentShell = parentShell;
		this.commandType = commandType;
	}

	@Override
	protected boolean accept(Object obj) {
		return StringUtils.isNotEmpty(getCommandId(obj));
	}

	@Override
	protected void perform(Object obj) {
		String commandId = getCommandId(obj);
		if (StringUtils.isNotEmpty(commandId)) {
			try {
				ContextHandlerUtils.executeCommand(commandId, serviceLocator,
						obj);
			} catch (Exception e) {
				StackTraceErrorDialog.openError(parentShell, "TODO", "TODO", e);
			}
		}
	}

	protected String getCommandId(Object obj) {
		IServerType serverType = getServerType(obj);
		if (serverType == null) {
			return null;
		}
		return Platform.getCommandIdProviderRegistry().getCommandId(serverType,
				commandType, obj);
	}

	protected IServerType getServerType(Object obj) {
		if (obj instanceof ITreeSimpleNode) {
			return ((ITreeSimpleNode) obj).getServer().getServerType();
		}
		return null;
	}

}
