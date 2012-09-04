package fr.opensagres.nosql.ide.ui.internal.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.core.shell.IShellCommand;
import fr.opensagres.nosql.ide.core.shell.IShellCommandListener;

public class ShellCommandsView extends ViewPart implements
		IShellCommandListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "fr.opensagres.nosql.ide.ui.views.ShellCommandsView";

	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	private final Map<IServer, ShellServerItem> serverTabItems;
	private CTabFolder tabFolder;

	public ShellCommandsView() {
		serverTabItems = new HashMap<IServer, ShellServerItem>();
	}

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		// Create the tabs
		tabFolder = new CTabFolder(parent, SWT.TOP);
		tabFolder.setBorderVisible(false);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		tabFolder.setSimple(false);
		tabFolder.setUnselectedImageVisible(false);
		tabFolder.setUnselectedCloseVisible(false);

		Platform.getShellCommandManagerRegistry().addShellListener(this);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		// MenuManager menuMgr = new MenuManager("#PopupMenu");
		// menuMgr.setRemoveAllWhenShown(true);
		// menuMgr.addMenuListener(new IMenuListener() {
		// public void menuAboutToShow(IMenuManager manager) {
		// ShellCommandsView.this.fillContextMenu(manager);
		// }
		// });
		// Menu menu = menuMgr.createContextMenu(viewer.getControl());
		// viewer.getControl().setMenu(menu);
		// getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		// IActionBars bars = getViewSite().getActionBars();
		// fillLocalPullDown(bars.getMenuManager());
		// fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		// action1 = new Action() {
		// public void run() {
		// showMessage("Action 1 executed");
		// }
		// };
		// action1.setText("Action 1");
		// action1.setToolTipText("Action 1 tooltip");
		// action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		// .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		//
		// action2 = new Action() {
		// public void run() {
		// showMessage("Action 2 executed");
		// }
		// };
		// action2.setText("Action 2");
		// action2.setToolTipText("Action 2 tooltip");
		// action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		// .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		// doubleClickAction = new Action() {
		// public void run() {
		// ISelection selection = viewer.getSelection();
		// Object obj = ((IStructuredSelection) selection)
		// .getFirstElement();
		// showMessage("Double-click detected on " + obj.toString());
		// }
		// };
	}

	private void hookDoubleClickAction() {
		// viewer.addDoubleClickListener(new IDoubleClickListener() {
		// public void doubleClick(DoubleClickEvent event) {
		// doubleClickAction.run();
		// }
		// });
	}

	private void showMessage(String message) {
		// MessageDialog.openInformation(viewer.getControl().getShell(),
		// "ShellCommandView", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tabFolder.setFocus();
	}

	public void commandAdded(IShellCommand command) {
		IServer server = command.getServer();
		// 1) Get or create new tab if needed for the server
		ShellServerItem item = serverTabItems.get(server);
		if (item == null || item.getTabItem().isDisposed()) {
			item = new ShellServerItem(server, tabFolder);
			serverTabItems.put(server, item);
		}
		// 2) Add the command to the viewer
		TableViewer viewer = item.getViewer();
		viewer.add(command);
		// 3) select the tab item linked to the command server
		tabFolder.setSelection(item.getTabItem());
	}

	@Override
	public void dispose() {
		super.dispose();

		Platform.getShellCommandManagerRegistry().removeShellListener(this);
	}

}
