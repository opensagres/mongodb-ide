package fr.opensagres.mongodb.ide.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.ui.actions.DeleteAction;
import fr.opensagres.mongodb.ide.ui.actions.NewServerAction;
import fr.opensagres.mongodb.ide.ui.actions.RefreshAction;
import fr.opensagres.mongodb.ide.ui.actions.server.StartServerAction;
import fr.opensagres.mongodb.ide.ui.actions.server.StopServerAction;
import fr.opensagres.mongodb.ide.ui.viewers.MongoContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.MongoLabelProvider;

public class ServerExplorer extends ViewPart {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.views.ServerExplorer";

	private ServerTreeViewer viewer;
	private Action newServerAction;
	private DeleteAction deleteAction;
	private Action refreshAction;
	private Action startServerAction;
	private Action stopServerAction;

	public void createPartControl(Composite parent) {
		viewer = new ServerTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(MongoContentProvider.getInstance());
		viewer.setLabelProvider(MongoLabelProvider.getInstance());
		viewer.setInput(Platform.getServerManager());

		createActions(viewer);
		hookContextMenu();
		contributeToActionBars();
		viewer.initialize(); 
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private void createActions(ISelectionProvider provider) {
		newServerAction = new NewServerAction();
		deleteAction = new DeleteAction(viewer);
		refreshAction = new RefreshAction(viewer);
		startServerAction = new StartServerAction(getSite().getShell(),
				provider);
		stopServerAction = new StopServerAction(getSite().getShell(), provider);
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
		manager.add(startServerAction);
		manager.add(stopServerAction);
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

}