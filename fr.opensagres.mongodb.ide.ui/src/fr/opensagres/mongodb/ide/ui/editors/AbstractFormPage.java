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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.mongodb.ide.ui.FormLayoutFactory;

/**
 * Abstract Form Page.
 * 
 */
public class AbstractFormPage extends FormPage {

	public AbstractFormPage(AbstractFormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public AbstractFormPage(String id, String title) {
		super(id, title);
	}

	@Override
	public AbstractFormEditor getEditor() {
		return (AbstractFormEditor) super.getEditor();
	}

	protected Composite createStaticSectionClient(FormToolkit toolkit,
			Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		container.setLayout(FormLayoutFactory
				.createSectionClientTableWrapLayout(false, 1));
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		container.setLayoutData(data);
		return container;
	}

	protected final FormText createClient(Composite section, String content,
			FormToolkit toolkit, IHyperlinkListener listener) {
		FormText text = toolkit.createFormText(section, true);
		try {
			text.setText(content, true, false);
		} catch (SWTException e) {
			text.setText(e.getMessage(), false, false);
		}
		text.addHyperlinkListener(listener);
		return text;
	}

}
