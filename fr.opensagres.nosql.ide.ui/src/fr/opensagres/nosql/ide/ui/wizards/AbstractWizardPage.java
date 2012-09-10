package fr.opensagres.nosql.ide.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import fr.opensagres.nosql.ide.ui.IErrorMessageAware;

public abstract class AbstractWizardPage extends WizardPage implements
		IErrorMessageAware {

	private boolean loaded;
	
	protected AbstractWizardPage(String pageName) {
		super(pageName);
		this.loaded = false;
	}

	protected AbstractWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public void createControl(Composite parent) {
		Composite container = createUI(parent);
		setControl(container);
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			if (!loaded) {
				onLoad();
				loaded = true;
			}
			validate();
		}
		super.setVisible(visible);
	}

	protected abstract void onLoad();

	protected abstract Composite createUI(Composite parent);

	public void validate() {
		boolean result = validateFields();
		if (result) {
			setErrorMessage(null);
		}
		super.setPageComplete(result);
		super.getContainer().updateButtons();
	}

	protected abstract boolean validateFields();

}
