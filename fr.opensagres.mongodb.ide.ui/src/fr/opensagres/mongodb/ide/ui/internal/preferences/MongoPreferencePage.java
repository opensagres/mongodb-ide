/*******************************************************************************
 * Copyright (c) 2011 Angelo ZERR.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:      
 *     Angelo Zerr <angelo.zerr@gmail.com> - adapt for XML Search.
 * Code comes from org.eclipse.wst.xml.xpath.ui.internal.preferences.XPathPrefencePage but there is not Header license.
 *******************************************************************************/
package fr.opensagres.mongodb.ide.ui.internal.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MongoPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		return new Composite(parent, SWT.NONE);
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
