package fr.opensagres.nosql.ide.ui.internal.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.ui.internal.viewers.ServerLabelProvider;
import fr.opensagres.nosql.ide.ui.internal.viewers.ServerTreeContentProvider;

public class ServersExplorer extends ViewPart {

	public static final String ID = "fr.opensagres.nosql.ide.ui.views.ServersExplorer";

	private ServerTreeViewer viewer;

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
	//	initializeActions(viewer);
		viewer.initialize();
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
