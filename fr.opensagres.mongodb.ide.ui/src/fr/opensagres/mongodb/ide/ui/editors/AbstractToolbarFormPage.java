package fr.opensagres.mongodb.ide.ui.editors;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public abstract class AbstractToolbarFormPage extends AbstractFormPage {

	public AbstractToolbarFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public AbstractToolbarFormPage(String id, String title) {
		super(id, title);
	}

	@Override
	protected final void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());

		IToolBarManager manager = form.getToolBarManager();
		((AbstractFormEditor) getEditor()).contributeToToolbar(manager);
		form.updateToolBar();
		form.setText(getFormTitleText());
		Image titleImage = getFormTitleImage();
		if (titleImage != null) {
			form.setImage(titleImage);
		}
		toolkit.decorateFormHeading(form.getForm());
		fillBody(managedForm, toolkit);
	}

	protected String getFormTitleText() {
		return getTitle();
	}

	protected Image getFormTitleImage() {
		return null;
	}

	protected abstract void fillBody(IManagedForm managedForm,
			FormToolkit toolkit);
}
