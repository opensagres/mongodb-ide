package fr.opensagres.mongodb.ide.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.ServerEvent;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

public class UIFieldsFactory {

	public static Label createServerField(Composite parent,
			FormToolkit toolkit, final Server server) {
		// Server
		Hyperlink serverHyperlink = toolkit.createHyperlink(parent,
				Messages.server_label, SWT.NONE);
		serverHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent ev) {
				ServerUI.editServer(server);

			}
		});
		final Label serverLabel = toolkit.createLabel(parent, "", SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		serverLabel.setLayoutData(gridData);

		final IServerListener serverListener = new IServerListener() {

			public void serverChanged(ServerEvent event) {
				serverLabel.setText(server.getName());

			}
		};
		server.addServerListener(serverListener);

		serverLabel.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				server.removeServerListener(serverListener);
			}
		});
		return serverLabel;

	}
}
