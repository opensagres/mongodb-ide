package fr.opensagres.mongodb.ide.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
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

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.extensions.IServerRunnerType;
import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.GridFSBucket;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.Users;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.actions.DeleteAction;
import fr.opensagres.mongodb.ide.ui.actions.OpenAction;
import fr.opensagres.mongodb.ide.ui.actions.RefreshAction;
import fr.opensagres.mongodb.ide.ui.actions.database.NewDatabaseAction;
import fr.opensagres.mongodb.ide.ui.actions.database.ShellRunnerAction;
import fr.opensagres.mongodb.ide.ui.actions.database.StartShellAction;
import fr.opensagres.mongodb.ide.ui.actions.database.StopShellAction;
import fr.opensagres.mongodb.ide.ui.actions.server.NewServerAction;
import fr.opensagres.mongodb.ide.ui.actions.server.ServerRunnerAction;
import fr.opensagres.mongodb.ide.ui.actions.server.StartServerAction;
import fr.opensagres.mongodb.ide.ui.actions.server.StopServerAction;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.internal.Trace;
import fr.opensagres.mongodb.ide.ui.viewers.MongoContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.MongoLabelProvider;

public class ServerExplorer extends ViewPart {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.views.ServerExplorer";

	private ServerTreeViewer viewer;

	private Action newServerAction;
	private Action startServerAction;
	private Action stopServerAction;
	private Action startShellAction;
	private Action stopShellAction;
	// private UnlockServerAction unlockServerAction;
	private Action refreshAction;
	private DeleteAction deleteAction;
	private OpenAction openAction;

	// Server actions
	private List<Action> serverStartActions;
	private List<Action> serverStopActions;

	// Database actions
	private Action newDatabaseAction;
	private List<Action> shellStartActions;
	private List<Action> shellStopActions;

	public void createPartControl(Composite parent) {
		viewer = new ServerTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		// Tree tree = viewer.getTree();
		// tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		// tree.setFont(parent.getFont());
		viewer.setContentProvider(MongoContentProvider.getInstance());
		viewer.setLabelProvider(MongoLabelProvider.getInstance());
		viewer.setInput(Platform.getServerManager());

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
					IStructuredSelection sel = (IStructuredSelection) event
							.getSelection();
					Object data = sel.getFirstElement();
					if (data instanceof Server) {
						ServerUI.editServer((Server) data);
					} else if (data instanceof Database) {
						ServerUI.editDatabase((Database) data);
					} else if (data instanceof GridFSBucket) {
						ServerUI.editGridFS((GridFSBucket) data);
					} else if (data instanceof Collection) {
						ServerUI.editCollection((Collection) data);
					} else if (data instanceof Users) {
						ServerUI.editUsers((Users) data);
					}
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
		serverStartActions = new ArrayList<Action>();
		serverStopActions = new ArrayList<Action>();
		java.util.Collection<IServerRunnerType> serverRunners = Platform
				.getServerRunnerRegistry().getRunners();
		for (IServerRunnerType serverRunner : serverRunners) {
			serverStartActions.add(new ServerRunnerAction(serverRunner, true,
					provider));
			serverStopActions.add(new ServerRunnerAction(serverRunner, false,
					provider));
		}

		// create the start server actions
		startServerAction = new StartServerAction(shell, provider,
				serverStartActions);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.mongodb.ide.server.start", startServerAction);

		// create the stop server action
		stopServerAction = new StopServerAction(shell, provider,
				serverStopActions);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.mongodb.ide.server.stop", stopServerAction);

		// Shell Actions
		shellStartActions = new ArrayList<Action>();
		shellStopActions = new ArrayList<Action>();
		java.util.Collection<IShellRunnerType> shellRunners = Platform
				.getShellRunnerRegistry().getRunners();
		for (IShellRunnerType shellRunner : shellRunners) {
			shellStartActions.add(new ShellRunnerAction(shellRunner, true,
					provider));
			if (shellRunner.getRunner().canSupportStop()) {
				shellStopActions.add(new ShellRunnerAction(shellRunner, false,
						provider));
			}
		}

		// create the start shell actions
		startShellAction = new StartShellAction(shell, provider,
				shellStartActions);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.mongodb.ide.shell.start", startShellAction);

		// create the stop shell action
		stopShellAction = new StopShellAction(shell, provider, shellStopActions);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.mongodb.ide.shell.stop", stopShellAction);

		// unlockServerAction = new UnlockServerAction(shell, provider);
		// actionBars.setGlobalActionHandler(
		// "fr.opensagres.mongodb.ide.server.unlock", unlockServerAction);

		// create the refresh action
		refreshAction = new RefreshAction(viewer);
		actionBars.setGlobalActionHandler(
				"fr.opensagres.mongodb.ide.server.refresh", refreshAction);

		// create the open action
		openAction = new OpenAction(provider);
		actionBars.setGlobalActionHandler("org.eclipse.ui.navigator.Open",
				openAction);

		deleteAction = new DeleteAction(viewer);
		newServerAction = new NewServerAction();
		newDatabaseAction = new NewDatabaseAction(viewer);

		// add toolbar buttons
		IContributionManager toolbarOfActionBars = actionBars
				.getToolBarManager();
		toolbarOfActionBars.add(startServerAction);
		toolbarOfActionBars.add(stopServerAction);
		toolbarOfActionBars.add(startShellAction);
		toolbarOfActionBars.add(stopShellAction);
		toolbarOfActionBars.add(refreshAction);
		toolbarOfActionBars.add(new Separator(
				IWorkbenchActionConstants.MB_ADDITIONS));

		// add toolbar menu
		IContributionManager menuOfActionBars = actionBars.getMenuManager();
		menuOfActionBars.add(newServerAction);
	}

	protected void fillContextMenu(Shell shell, IMenuManager menu) {
		// get selection but avoid no selection or multiple selection
		Server server = null;
		Database database = null;
		Collection collection = null;
		Users users = null;
		GridFSBucket gridFSBucket = null;
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (selection.size() == 1) {
			Object obj = selection.getFirstElement();
			if (obj instanceof Server) {
				server = (Server) obj;
			} else if (obj instanceof Database) {
				database = (Database) obj;
			} else if (obj instanceof Collection) {
				collection = (Collection) obj;
			} else if (obj instanceof Users) {
				users = (Users) obj;
			} else if (obj instanceof GridFSBucket) {
				gridFSBucket = (GridFSBucket) obj;
			}
		}

		// new action
		MenuManager newMenu = new MenuManager(Messages.actionNew);
		fillNewContextMenu(null, selection, newMenu);
		menu.add(newMenu);

		// open action
		if (server != null || database != null || collection != null
				|| users != null || gridFSBucket != null) {
			menu.add(openAction);
		}

		menu.add(refreshAction);
		menu.add(new Separator());
		menu.add(deleteAction);

		if (server != null) {
			menu.add(new Separator());
			// Start server actions
			for (Action action : serverStartActions) {
				menu.add(action);
			}
			menu.add(new Separator());
			// Stop server actions
			for (Action action : serverStopActions) {
				menu.add(action);
			}

			// menu.add(startServerAction);
			// menu.add(stopServerAction);
			// menu.add(unlockServerAction);
		}

		if (database != null) {
			menu.add(new Separator());
			// Start shell actions
			for (Action action : shellStartActions) {
				menu.add(action);
			}
			menu.add(new Separator());
			// Stop shell actions
			for (Action action : shellStopActions) {
				menu.add(action);
			}
		}

	}

	private void fillNewContextMenu(Shell shell, ISelection selection,
			IMenuManager menu) {
		menu.add(newServerAction);
		menu.add(newDatabaseAction);
	}

	// private void initializeActions(ISelectionProvider provider) {
	// newServerAction = new NewServerAction();
	// deleteAction = new DeleteAction(viewer);
	// refreshAction = new RefreshAction(viewer);
	// startServerAction = new StartServerAction(getSite().getShell(),
	// provider);
	// stopServerAction = new StopServerAction(getSite().getShell(), provider);
	// }

	// private void contributeToActionBars() {
	// IActionBars bars = getViewSite().getActionBars();
	// fillLocalPullDown(bars.getMenuManager());
	// fillLocalToolBar(bars.getToolBarManager());
	// }

	// private void fillLocalPullDown(IMenuManager manager) {
	// manager.add(newServerAction);
	// manager.add(new Separator());
	// }
	//
	// private void fillContextMenu(IMenuManager manager) {
	// manager.add(newServerAction);
	// manager.add(new Separator());
	// }
	//
	// private void fillLocalToolBar(IToolBarManager manager) {
	// manager.add(startServerAction);
	// manager.add(stopServerAction);
	// manager.add(deleteAction);
	// manager.add(refreshAction);
	// manager.add(newServerAction);
	// }

	// private void hookContextMenu() {
	// MenuManager menuMgr = new MenuManager("#PopupMenu");
	// menuMgr.setRemoveAllWhenShown(true);
	// menuMgr.addMenuListener(new IMenuListener() {
	// public void menuAboutToShow(IMenuManager manager) {
	//
	// IStructuredSelection selection = (IStructuredSelection) viewer
	// .getSelection();
	//
	// if (selection.isEmpty()) {
	// ServerExplorer.this.fillContextMenu(manager);
	// return;
	// }
	// }
	// });
	// Menu menu = menuMgr.createContextMenu(viewer.getControl());
	// viewer.getControl().setMenu(menu);
	// getSite().registerContextMenu(menuMgr, viewer);
	// }

	public void setFocus() {
		viewer.getControl().setFocus();
	}

}