package fr.opensagres.nosql.ide.ui.internal.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerRunnerType;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.ui.internal.actions.servers.RunServerAction;
import fr.opensagres.nosql.ide.ui.internal.actions.servers.ServerRunnerAction;
import fr.opensagres.nosql.ide.ui.internal.viewers.ServerLabelProvider;
import fr.opensagres.nosql.ide.ui.internal.viewers.ServerTreeContentProvider;

public class ServersExplorer extends ViewPart {

	public static final String ID = "fr.opensagres.nosql.ide.ui.views.ServersExplorer";

	private ServerTreeViewer viewer;

	// Server actions
	private Action startServerAction;
	private Action stopServerAction;
	private List<Action> serverStartActions;
	private List<Action> serverStopActions;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new ServerTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(ServerTreeContentProvider.getInstance());
		viewer.setLabelProvider(ServerLabelProvider.getInstance());
		viewer.setInput(Platform.getServerTypeRegistry().getTypes());

		deferInitialization();
	}

	private void deferInitialization() {
		initializeActions(viewer);
		viewer.initialize();
	}

	/**
	 * Initialize actions
	 * 
	 * @param provider
	 *            a selection provider
	 */
	private void initializeActions(ISelectionProvider provider) {
		Shell shell = getSite().getShell();
		IActionBars actionBars = getViewSite().getActionBars();

		// Server Actions
		// serverStartActions = new ArrayList<Action>();
		// serverStopActions = new ArrayList<Action>();
		// java.util.Collection<IServerRunnerType> serverRunners = Platform
		// .getServerRunnerRegistry().getRunners();
		// for (IServerRunnerType serverRunner : serverRunners) {
		// serverStartActions.add(new ServerRunnerAction(serverRunner, true,
		// provider));
		// serverStopActions.add(new ServerRunnerAction(serverRunner, false,
		// provider));
		// }

		Map<IServerType, Collection<Action>> startRunnerActionsMap = new HashMap<IServerType, Collection<Action>>();
		Map<IServerType, Collection<Action>> stopRunnerActionsMap = new HashMap<IServerType, Collection<Action>>();
		java.util.Collection<IServerRunnerType> serverRunners = Platform
				.getServerRunnerRegistry().getRunners();
		IServerType serverType = null;
		Collection<Action> runnerActions = null;
		for (IServerRunnerType serverRunner : serverRunners) {
			serverType = serverRunner.getServerType();
			// Add start runner action
			runnerActions = startRunnerActionsMap.get(serverType);
			if (runnerActions == null) {
				runnerActions = new ArrayList<Action>();
				startRunnerActionsMap.put(serverType, runnerActions);
			}
			runnerActions.add(new ServerRunnerAction(serverRunner, true,
					provider));
			// Add stop runner action
			runnerActions = stopRunnerActionsMap.get(serverType);
			if (runnerActions == null) {
				runnerActions = new ArrayList<Action>();
				stopRunnerActionsMap.put(serverType, runnerActions);
			}
			runnerActions.add(new ServerRunnerAction(serverRunner, false,
					provider));

		}
		// create the start server actions
		startServerAction = new RunServerAction(true, provider,
				startRunnerActionsMap);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.nosql.ide.server.start", startServerAction);

		// create the stop server action
		stopServerAction = new RunServerAction(false, provider,
				stopRunnerActionsMap);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.nosql.ide.server.stop", stopServerAction);

		// add toolbar buttons
		IContributionManager toolbarOfActionBars = actionBars
				.getToolBarManager();
		toolbarOfActionBars.add(startServerAction);
		toolbarOfActionBars.add(stopServerAction);
		// toolbarOfActionBars.add(startShellAction);
		// toolbarOfActionBars.add(stopShellAction);
		// toolbarOfActionBars.add(refreshAction);
		toolbarOfActionBars.add(new Separator(
				IWorkbenchActionConstants.MB_ADDITIONS));
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
