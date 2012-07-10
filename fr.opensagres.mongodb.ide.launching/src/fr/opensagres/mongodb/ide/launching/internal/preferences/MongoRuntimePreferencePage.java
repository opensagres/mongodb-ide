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
package fr.opensagres.mongodb.ide.launching.internal.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.opensagres.mongodb.ide.core.IMongoRuntimeManager;
import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.launching.internal.Activator;
import fr.opensagres.mongodb.ide.launching.internal.Messages;

public class MongoRuntimePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private InstalledRuntimesBlock runtimesBlock;

	public MongoRuntimePreferencePage() {
		super();
		// only used when page is shown programatically
		setTitle(Messages.MongoRuntimePreferencePage_title);
		setDescription(Messages.MongoRuntimePreferencePage_desc);
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite ancestor) {
		initializeDialogUnits(ancestor);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		ancestor.setLayout(layout);

		runtimesBlock = new InstalledRuntimesBlock();
		runtimesBlock.createControl(ancestor);
		Control control = runtimesBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp...

		initDefaultInstall();
		runtimesBlock
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						MongoRuntime runtime = getCurrentDefaultRuntime();
						if (runtime == null) {
							setValid(false);
							//setErrorMessage(Messages.MongoRuntimePreferencePage_2);
						} else {
							setValid(true);
							setErrorMessage(null);
						}
					}
				});
		applyDialogFont(ancestor);
		return ancestor;
	}

	@Override
	public boolean performOk() {
		try {
			IMongoRuntimeManager manager = Platform.getMongoRuntimeManager();
			manager.setRuntimes(runtimesBlock.getRuntimes());
			return true;
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		
		
//		MongoRuntime runtimeType = getCurrentDefaultRuntime();
//		if (runtimeType == null) {
//			setErrorMessage("Please select a XPath runtime");
//			return false;
//		}
//		runtimesBlock.saveColumnSettings();
		//MongoRuntimeManager.getDefault().setDefaultRuntime(runtimeType);
	}

	private void initDefaultInstall() {
//		MongoRuntime runtimeType = MongoRuntimeManager.getDefault()
//				.getDefaultRuntime();
//		verifyDefaultVM(runtimeType);

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

	private void verifyDefaultVM(MongoRuntime install) {
		if (install != null) {
			runtimesBlock.setCheckedInstall(install);
		} else {
			runtimesBlock.setCheckedInstall(null);
		}
	}

	private MongoRuntime getCurrentDefaultRuntime() {
		return runtimesBlock.getCheckedInstall();
	}

}
