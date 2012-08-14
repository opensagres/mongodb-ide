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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.internal.Validator;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectDatabaseWizardPage;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractWizardPage;

/**
 * New Database wizard page.
 * 
 */
public class NewCollectionWizardPage extends AbstractSelectDatabaseWizardPage {

	private static final String PAGE_NAME = "NewCollectionWizardPage";

	private Label serverNameLabel;
	private Label databaseNameLabel;
	private Text collectionNameText;

	private Button openCollectionEditorCheckbox;

	public NewCollectionWizardPage() {
		super(PAGE_NAME);
		super.setTitle(Messages.NewCollectionWizardPage_title);
		super.setDescription(Messages.NewCollectionWizardPage_desc);
		// super.setImageDescriptor(ImageResources
		// .getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_COLLECTION));
	}

	@Override
	protected void createUIContent(Composite container) {
		super.createUIContent(container);

		// Collection Name
		Label collectionNameLabel = new Label(container, SWT.NONE);
		collectionNameLabel.setText(Messages.collection_label);
		collectionNameText = new Text(container, SWT.BORDER);
		collectionNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		collectionNameText
				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Open database editor at end?
		openCollectionEditorCheckbox = new Button(container, SWT.CHECK);
		openCollectionEditorCheckbox
				.setText(Messages.NewCollectionWizardPage_openCollectionEditorCheckbox_label);
		openCollectionEditorCheckbox.setSelection(true);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		openCollectionEditorCheckbox.setLayoutData(data);
	}

	@Override
	protected boolean validateFields() {
		// Validate server
		if (super.validateFields()) {
			// Collection name validation
			return Validator.validateCollectionName(
					collectionNameText.getText(), this);
		}
		return false;
	}

	public String getCollectionName() {
		return collectionNameText.getText();
	}

	public boolean isOpenCollectionEditor() {
		return openCollectionEditorCheckbox.getSelection();
	}

}
