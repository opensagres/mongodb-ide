package fr.opensagres.mongodb.ide.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.actions.DeleteAction;
import fr.opensagres.mongodb.ide.ui.actions.NewServerAction;
import fr.opensagres.mongodb.ide.ui.actions.RefreshAction;
import fr.opensagres.mongodb.ide.ui.viewers.MongoContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.MongoLabelProvider;

public class ServerExplorer extends ViewPart implements IServerListener {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.views.ServerExplorer";

	private TreeViewer viewer;
	private Action newServerAction;
	private DeleteAction deleteAction;	
	private Action refreshAction;

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(MongoContentProvider.getInstance());
		viewer.setLabelProvider(MongoLabelProvider.getInstance());
		viewer.setInput(Platform.getServerManager());

		createActions();
		hookContextMenu();
		contributeToActionBars();
		Platform.getServerManager().addListener(this);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private void createActions() {
		newServerAction = new NewServerAction();
		deleteAction = new DeleteAction(viewer);
		refreshAction = new RefreshAction(viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(newServerAction);
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(newServerAction);
		manager.add(new Separator());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(deleteAction);
		manager.add(refreshAction);
		manager.add(newServerAction);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {

				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();

				if (selection.isEmpty()) {
					ServerExplorer.this.fillContextMenu(manager);
					return;
				}
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	@Override
	public void dispose() {
		super.dispose();
		Platform.getServerManager().removeListener(this);
	}
	
	public void serverAdded(Server server) {
		viewer.add(Platform.getServerManager(), server);
	}
	
	public void serverRemoved(Server server) {
		viewer.remove(server);
	}
}