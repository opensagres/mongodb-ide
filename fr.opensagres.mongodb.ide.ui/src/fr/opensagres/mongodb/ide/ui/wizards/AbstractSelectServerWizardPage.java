/*******************************************************************************
 * Copyright (C) 2012 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package fr.opensagres.mongodb.ide.ui.wizards;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.internal.Validator;
import fr.opensagres.mongodb.ide.ui.viewers.ServerContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.ServerLabelProvider;

/**
 * Abstract select server wizard page.
 * 
 */
public abstract class AbstractSelectServerWizardPage extends AbstractWizardPage {

	private ComboViewer serverViewer;

	public AbstractSelectServerWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	protected Composite createUI(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		createUIContent(container);

		return container;
	}

	protected void createUIContent(Composite container) {

		// Display list of server.
		Label serverLabel = new Label(container, SWT.NONE);
		serverLabel.setText(Messages.server_label);
		serverViewer = new ComboViewer(container, SWT.BORDER | SWT.READ_ONLY);
		serverViewer.setLabelProvider(ServerLabelProvider.getInstance());
		serverViewer.setContentProvider(ServerContentProvider.getInstance());
		serverViewer.setInput(Platform.getServerManager().getServers());
		serverViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		serverViewer.getCombo().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				serverChanged();
			}

		});
	}

	protected void serverChanged() {
		validate();
	}

	@Override
	protected boolean validateFields() {
		// Validate server
		Server server = getSelectedServer();
		return (Validator.validateServer(server, this));
	}

	public Server getSelectedServer() {
		return serverViewer.getSelection().isEmpty() ? null
				: (Server) ((IStructuredSelection) serverViewer.getSelection())
						.getFirstElement();
	}

	@Override
	protected void onLoad() {
		Server server = getInitialServer();
		if (server != null) {
			serverViewer.setSelection(new StructuredSelection(server));
		}
	}

	private Server getInitialServer() {
		return ((AbstractSelectNodeWizard) getWizard()).getInitialServer();
	}
}
