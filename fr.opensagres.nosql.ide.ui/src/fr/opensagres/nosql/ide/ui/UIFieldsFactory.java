package fr.opensagres.nosql.ide.ui;

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

import fr.opensagres.nosql.ide.core.IServerListener;
import fr.opensagres.nosql.ide.core.ServerEvent;
import fr.opensagres.nosql.ide.core.model.ICollection;
import fr.opensagres.nosql.ide.core.model.IDatabase;
import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.ui.internal.Messages;

public class UIFieldsFactory {

	public static Label createServerField(Composite parent,
			FormToolkit toolkit, final IServer server) {
		// Server
		Hyperlink serverHyperlink = toolkit.createHyperlink(parent,
				Messages.server_label, SWT.NONE);
		serverHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent ev) {
				ServerUI.openEditor(server);
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
	
	public static Label createDatabaseField(Composite parent,
			FormToolkit toolkit, final IDatabase database) {
		// Database
		Hyperlink databaseHyperlink = toolkit.createHyperlink(parent,
				Messages.database_label, SWT.NONE);
		databaseHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent ev) {
				//ServerUI.editDatabase(database);

			}
		});
		final Label databaseLabel = toolkit.createLabel(parent, "", SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		databaseLabel.setLayoutData(gridData);

//		final IDatabaseListener databaseListener = new IDatabaseListener() {
//
//			public void databaseChanged(DatabaseEvent event) {
//				databaseLabel.setText(database.getName());
//
//			}
//		};
//		database.addDatabaseListener(databaseListener);
//
//		databaseLabel.addDisposeListener(new DisposeListener() {
//
//			public void widgetDisposed(DisposeEvent e) {
//				database.removeDatabaseListener(databaseListener);
//			}
//		});
		return databaseLabel;

	}
	
	public static Label createCollectionField(Composite parent,
			FormToolkit toolkit, final ICollection collection) {
		// Collection
		Hyperlink collectionHyperlink = toolkit.createHyperlink(parent,
				Messages.collection_label, SWT.NONE);
		collectionHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent ev) {
				//ServerUI.editCollection(collection);

			}
		});
		final Label collectionLabel = toolkit.createLabel(parent, "", SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		collectionLabel.setLayoutData(gridData);

//		final ICollectionListener collectionListener = new ICollectionListener() {
//
//			public void collectionChanged(CollectionEvent event) {
//				collectionLabel.setText(collection.getName());
//
//			}
//		};
//		collection.addCollectionListener(collectionListener);
//
//		collectionLabel.addDisposeListener(new DisposeListener() {
//
//			public void widgetDisposed(DisposeEvent e) {
//				collection.removeCollectionListener(collectionListener);
//			}
//		});
		return collectionLabel;

	}	
}
