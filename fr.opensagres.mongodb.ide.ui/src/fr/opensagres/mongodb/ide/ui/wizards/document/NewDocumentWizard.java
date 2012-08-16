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
package fr.opensagres.mongodb.ide.ui.wizards.document;

import fr.opensagres.mongodb.ide.core.model.Document;
import fr.opensagres.mongodb.ide.ui.ServerUI;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;

/**
 * New Document wizard.
 * 
 */
public class NewDocumentWizard extends AbstractSelectNodeWizard {

	public static final String ID = "fr.opensagres.mongodb.ide.ui.wizards.document.NewDocumentWizard";

	private final NewDocumentWizardPage page;

	public NewDocumentWizard() {
		super.setWindowTitle(Messages.NewDocumentWizard_title);
		//super.setDefaultPageImageDescriptor(ImageResources
		//		.getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_DOCUMENT));
		page = new NewDocumentWizardPage();
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}

	@Override
	protected boolean doPerformFinish() throws Exception {
//		String documentName = page.getDocumentName();
//		// Create Document
//		Document document = page.getSelectedServer().createDocument(
//				documentName);
//		if (page.isOpenDocumentEditor()) {
//			// Open document in an editor.
//			ServerUI.editDocument(document);
//		}
		return true;
	}
}
