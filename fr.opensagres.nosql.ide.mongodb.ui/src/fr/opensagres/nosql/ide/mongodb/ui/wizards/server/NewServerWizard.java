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
package fr.opensagres.nosql.ide.mongodb.ui.wizards.server;

import fr.opensagres.nosql.ide.core.model.IServer;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServer;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.wizards.server.AbstractNewServerWizard;

/**
 * New Server wizard.
 * 
 */
public class NewServerWizard extends AbstractNewServerWizard {

	public static final String ID = "fr.opensagres.nosql.ide.mongodb.ui.wizards.server.NewServerWizard";

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
	protected IServer createServer() {
		IServer server = new MongoServer(page.getName(), page.getMongoURI());
		server.setRuntime(page.getRuntime());
		return server;
	}

}
