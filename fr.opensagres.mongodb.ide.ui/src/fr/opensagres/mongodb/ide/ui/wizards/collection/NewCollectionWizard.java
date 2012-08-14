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
package fr.opensagres.mongodb.ide.ui.wizards.collection;

import fr.opensagres.mongodb.ide.core.model.Collection;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;

/**
 * New Database wizard.
 * 
 */
public class NewCollectionWizard extends AbstractSelectNodeWizard {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.wizards.collection.NewCollectionWizard";

	private final NewCollectionWizardPage page;

	public NewCollectionWizard() {
		super.setWindowTitle(Messages.NewCollectionWizard_title);
		super.setDefaultPageImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_DATABASE));
		page = new NewCollectionWizardPage();
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}

	@Override
	protected boolean doPerformFinish() throws Exception {
		String collectionName = page.getCollectionName();
		String databaseName = null;
		Database selectedDatabase = page.getSelectedDatabase();
		// if (selectedDatabase == null) {
		databaseName = page.getDatabaseName();
		// }
		Collection collection = page.getSelectedServer().createCollection(
				selectedDatabase, databaseName, collectionName);
		if (page.isOpenCollectionEditor()) {
			// Open collection in an editor.
			ServerUI.editCollection(collection);
		}
		return true;
	}
}
