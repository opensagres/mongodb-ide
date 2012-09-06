/*******************************************************************************
 * Copyright (c) 2010 Angelo ZERR.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:      
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package fr.opensagres.nosql.ide.ui.internal.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.opensagres.nosql.ide.core.IServerRuntimeManager;
import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.ui.internal.Activator;
import fr.opensagres.nosql.ide.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.viewers.ServerTypeContentProvider;
import fr.opensagres.nosql.ide.ui.viewers.ServerTypeLabelProvider;

public class ServerRuntimePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private InstalledRuntimesBlock runtimesBlock;

	public ServerRuntimePreferencePage() {
		super();
		// only used when page is shown programatically
		setTitle(Messages.ServerRuntimePreferencePage_title);
		setDescription(Messages.ServerRuntimePreferencePage_desc);
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);

		runtimesBlock = new InstalledRuntimesBlock();
		runtimesBlock.createControl(parent);
		Control control = runtimesBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp...

		initDefaultInstall();
		runtimesBlock
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IServerRuntime runtime = getCurrentDefaultRuntime();
						if (runtime == null) {
							setValid(false);
							// setErrorMessage(Messages.MongoRuntimePreferencePage_2);
						} else {
							setValid(true);
							setErrorMessage(null);
						}
					}
				});
		applyDialogFont(parent);
		return parent;
	}

	@Override
	public boolean performOk() {
		try {
			IServerRuntimeManager manager = Platform.getServerRuntimeManager();
			manager.setRuntimes(runtimesBlock.getRuntimes());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// MongoRuntime runtimeType = getCurrentDefaultRuntime();
		// if (runtimeType == null) {
		// setErrorMessage("Please select a XPath runtime");
		// return false;
		// }
		// runtimesBlock.saveColumnSettings();
		// MongoRuntimeManager.getDefault().setDefaultRuntime(runtimeType);
	}

	private void initDefaultInstall() {
		// MongoRuntime runtimeType = MongoRuntimeManager.getDefault()
		// .getDefaultRuntime();
		// verifyDefaultVM(runtimeType);

		// IXPathEvaluatorType realDefault = JAXPRuntime.getDefaultRuntime();
		// if (realDefault != null) {
		// IXPathEvaluatorType[] installs = runtimesBlock.getRuntimes();
		// for (IXPathEvaluatorType fakeInstall : installs) {
		// if (fakeInstall.getId().equals(realDefault.getId())) {
		// verifyDefaultVM(fakeInstall);
		// break;
		// }
		// }
		// }
	}

	private IServerRuntime getCurrentDefaultRuntime() {
		return runtimesBlock.getCheckedInstall();
	}

}
