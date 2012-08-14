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
package fr.opensagres.mongodb.ide.ui.wizards.server;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractNewWizard;

/**
 * New Server wizard.
 * 
 */
public class NewServerWizard extends AbstractNewWizard {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.wizards.server.NewServerWizard";

	private final NewServerWizardPage page;

	public NewServerWizard() {
		super();
		super.setWindowTitle(Messages.NewServerWizard_title);
		page = new NewServerWizardPage();
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	@Override
	protected boolean doPerformFinish() throws Exception {
		Server server = new Server(page.getName(), page.getMongoURI());
		server.setRuntime(page.getRuntime());
		Platform.getServerManager().addServer(server);
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
