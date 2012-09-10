package fr.opensagres.nosql.ide.ui.internal.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerRunnerType;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;
import fr.opensagres.nosql.ide.core.model.NodeTypeConstants;
import fr.opensagres.nosql.ide.ui.internal.Trace;
import fr.opensagres.nosql.ide.ui.internal.actions.DeleteAction;
import fr.opensagres.nosql.ide.ui.internal.actions.OpenAction;
import fr.opensagres.nosql.ide.ui.internal.actions.RefreshAction;
import fr.opensagres.nosql.ide.ui.internal.actions.database.NewCollectionAction;
import fr.opensagres.nosql.ide.ui.internal.actions.database.NewDatabaseAction;
import fr.opensagres.nosql.ide.ui.internal.actions.database.NewDocumentAction;
import fr.opensagres.nosql.ide.ui.internal.actions.servers.NewServerAction;
import fr.opensagres.nosql.ide.ui.internal.actions.servers.RunServerAction;
import fr.opensagres.nosql.ide.ui.internal.actions.servers.ServerRunnerAction;
import fr.opensagres.nosql.ide.ui.viewers.server.ServerLabelProvider;
import fr.opensagres.nosql.ide.ui.viewers.server.ServerTreeContentProvider;

public class ServersExplorer extends ViewPart {

	public static final String ID = "fr.opensagres.nosql.ide.ui.views.ServersExplorer";

	private ServerTreeViewer viewer;

	// Server type actions
	Action newServerAction;

	// Generic actions
	private OpenAction openAction;
	private DeleteAction deleteAction;
	private RefreshAction refreshAction;

	// Server actions
	private Action startServerAction;
	private Action stopServerAction;

	// Database actions
	private Action newDatabaseAction;

	// Collection actions
	private Action newCollectionAction;

	// Document actions
	private Action newDocumentAction;

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
		// Open Server, Database, Collection editor when user double click on
		// the node
		viewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				try {
					openAction.run();
				} catch (Exception e) {
					if (Trace.SEVERE) {
						Trace.trace(Trace.STRING_SEVERE,
								"Could not open server", e);
					}
				}
			}
		});

		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		final Shell shell = getSite().getShell();
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillContextMenu(shell, mgr);
			}
		});
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
		getSite().setSelectionProvider(viewer);

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

		createServerTypeActions(provider, actionBars);
		createServerActions(provider, actionBars);
		createGenericActions(provider, actionBars);
		createDatabaseActions(provider, actionBars);
		createCollectionActions(provider, actionBars);
		createDocumentActions(provider, actionBars);

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

	private void createServerTypeActions(ISelectionProvider provider,
			IActionBars actionBars) {
		newServerAction = new NewServerAction(provider, getSite(), getSite()
				.getShell());
	}

	private void createServerActions(ISelectionProvider provider,
			IActionBars actionBars) {
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

	}

	private void createGenericActions(ISelectionProvider provider,
			IActionBars actionBars) {
		// create the open action
		openAction = new OpenAction(provider, getSite(), getSite().getShell());
		actionBars.setGlobalActionHandler("org.eclipse.ui.navigator.Open",
				openAction);
		deleteAction = new DeleteAction(viewer);
		actionBars.setGlobalActionHandler("org.eclipse.ui.navigator.Delete",
				deleteAction);
		refreshAction = new RefreshAction(viewer);
	}

	private void createDatabaseActions(ISelectionProvider provider,
			IActionBars actionBars) {
		newDatabaseAction = new NewDatabaseAction(provider, getSite(),
				getSite().getShell());
	}

	private void createCollectionActions(ISelectionProvider provider,
			IActionBars actionBars) {
		newCollectionAction = new NewCollectionAction(provider, getSite(),
				getSite().getShell());
	}

	private void createDocumentActions(ISelectionProvider provider,
			IActionBars actionBars) {
		newDocumentAction = new NewDocumentAction(provider, getSite(),
				getSite().getShell());
	}

	protected void fillContextMenu(Shell shell, IMenuManager menu) {
		Object selectedElement = getFirstSelectedElement(viewer);
		if (selectedElement != null) {
			if (selectedElement instanceof IServerType) {
				menu.add(newServerAction);
			} else if (selectedElement instanceof ITreeSimpleNode) {

				menu.add(openAction);
				menu.add(refreshAction);
				menu.add(deleteAction);
				menu.add(new Separator());

				switch (((ITreeSimpleNode) selectedElement).getType()) {
				case NodeTypeConstants.Server:
					menu.add(newDatabaseAction);
					break;
				case NodeTypeConstants.CollectionsCategory:
				case NodeTypeConstants.Database:
					menu.add(newCollectionAction);
					break;
				case NodeTypeConstants.Collection:
					menu.add(newDocumentAction);
					break;
				}
			}
		}
	}

	private Object getFirstSelectedElement(ISelectionProvider provider) {
		IStructuredSelection selection = (IStructuredSelection) provider
				.getSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return selection.getFirstElement();
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
