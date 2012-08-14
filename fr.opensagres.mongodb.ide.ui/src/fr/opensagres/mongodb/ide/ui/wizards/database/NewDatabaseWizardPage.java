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
package fr.opensagres.mongodb.ide.ui.wizards.database;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.internal.Validator;
import fr.opensagres.mongodb.ide.ui.viewers.ServerContentProvider;
import fr.opensagres.mongodb.ide.ui.viewers.ServerLabelProvider;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectNodeWizard;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractSelectServerWizardPage;
import fr.opensagres.mongodb.ide.ui.wizards.AbstractWizardPage;

/**
 * New Database wizard page.
 * 
 */
public class NewDatabaseWizardPage extends AbstractSelectServerWizardPage {

	private static final String PAGE_NAME = "NewDatabaseWizardPage";

	private Text databaseNameText;
	private Button openDatabaseEditorCheckbox;

	public NewDatabaseWizardPage() {
		super(PAGE_NAME);
		super.setTitle(Messages.NewDatabaseWizardPage_title);
		super.setDescription(Messages.NewDatabaseWizardPage_desc);
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_WIZBAN_NEW_DATABASE));
	}

	@Override
	protected void createUIContent(Composite container) {
		super.createUIContent(container);

		// Database Name
		Label databaseNameLabel = new Label(container, SWT.NONE);
		databaseNameLabel.setText(Messages.database_label);
		databaseNameText = new Text(container, SWT.BORDER);
		databaseNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		databaseNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Open database editor at end?
		openDatabaseEditorCheckbox = new Button(container, SWT.CHECK);
		openDatabaseEditorCheckbox
				.setText(Messages.NewDatabaseWizardPage_openDatabaseEditorCheckbox_label);
		openDatabaseEditorCheckbox.setSelection(true);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		openDatabaseEditorCheckbox.setLayoutData(data);
	}

	@Override
	protected boolean validateFields() {
		// Validate server
		if (super.validateFields()) {
			// Database Name validation
			return Validator.validateDatatabaseName(databaseNameText.getText(),
					this);
		}
		return false;
	}

	public String getDatabaseName() {
		return databaseNameText.getText();
	}

	public boolean isOpenDatabaseEditor() {
		return openDatabaseEditorCheckbox.getSelection();
	}

}
