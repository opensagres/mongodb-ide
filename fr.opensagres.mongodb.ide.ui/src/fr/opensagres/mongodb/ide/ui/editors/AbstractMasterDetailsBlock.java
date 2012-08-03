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
package fr.opensagres.mongodb.ide.ui.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import fr.opensagres.mongodb.ide.ui.internal.ImageResources;

/**
 * Abstract Master detail block.
 * 
 */
public abstract class AbstractMasterDetailsBlock extends MasterDetailsBlock
		implements IDetailsPageProvider {

	private final AbstractMasterDetailsFormPage formPage;

	public AbstractMasterDetailsBlock(AbstractMasterDetailsFormPage formPage) {
		this.formPage = formPage;
	}

	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		//		haction.setToolTipText(Messages.getString("ScrolledPropertiesBlock.horizontal")); //$NON-NLS-1$
		haction.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_TH_HORIZONTAL_16));
		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		//		vaction.setToolTipText(Messages.getString("ScrolledPropertiesBlock.vertical")); //$NON-NLS-1$
		vaction.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_TH_VERTICAL_16));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}

	protected void registerPages(org.eclipse.ui.forms.DetailsPart detailsPart) {
		detailsPart.setPageProvider(this);
	}

	public AbstractMasterDetailsFormPage getFormPage() {
		return formPage;
	}

	public FormEditor getEditor() {
		return getFormPage().getEditor();
	}
}
