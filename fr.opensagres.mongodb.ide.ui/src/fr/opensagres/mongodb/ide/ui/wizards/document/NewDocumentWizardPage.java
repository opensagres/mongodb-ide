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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectDatabaseWizardPage;

/**
 * New Database wizard page.
 * 
 */
public class NewDocumentWizardPage extends AbstractSelectDatabaseWizardPage {

	private static final String PAGE_NAME = "NewDocumentWizardPage";

	private Text documentNameText;

	private Button openDocumentEditorCheckbox;

	public NewDocumentWizardPage() {
		super(PAGE_NAME);
		super.setTitle(Messages.NewDocumentWizardPage_title);
		super.setDescription(Messages.NewDocumentWizardPage_desc);
		// super.setImageDescriptor(ImageResources
		// .getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_COLLECTION));
	}

	@Override
	protected void createUIContent(Composite container) {
		super.createUIContent(container);

		// Open database editor at end?
		openDocumentEditorCheckbox = new Button(container, SWT.CHECK);
		openDocumentEditorCheckbox
				.setText(Messages.NewDocumentWizardPage_openDocumentEditorCheckbox_label);
		openDocumentEditorCheckbox.setSelection(true);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		openDocumentEditorCheckbox.setLayoutData(data);
	}

	@Override
	protected boolean validateFields() {
		// Validate server
		if (super.validateFields()) {
//			// Document name validation
//			return Validator.validateDocumentName(
//					documentNameText.getText(), this);
		}
		return false;
	}

	public boolean isOpenDocumentEditor() {
		return openDocumentEditorCheckbox.getSelection();
	}

}
