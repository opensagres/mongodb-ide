package fr.opensagres.nosql.ide.ui.internal.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.ui.internal.viewers.ShellCommandLabelProvider;
import fr.opensagres.nosql.ide.ui.internal.viewers.ShellCommandContentProvider;

public class ShellServerItem {

	private final IServer server;
	private final CTabItem tabItem;
	private final TableViewer viewer;

	public ShellServerItem(IServer server, CTabFolder tabFolder) {
		this.server = server;
		this.tabItem = new CTabItem(tabFolder, SWT.NONE);
		Composite c1 = new Composite(tabFolder, SWT.NONE);
		c1.setLayout(new GridLayout());
		this.viewer = new TableViewer(c1, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setLabelProvider(ShellCommandLabelProvider.getInstance());
		viewer.setContentProvider(ShellCommandContentProvider.getInstance());
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		tabItem.setControl(c1);
		tabItem.setText(server.getName());
		tabItem.setImage(ImageResources.getImage(ImageResources.IMG_SERVER_16));
		tabItem.setShowClose(true);
	}

	public IServer getServer() {
		return server;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public CTabItem getTabItem() {
		return tabItem;
	}

}
