package fr.opensagres.mongodb.ide.ui.editors;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class AbstractMasterDetailsFormPage extends
		AbstractToolbarFormPage {

	private final AbstractMasterDetailsBlock block;

	public AbstractMasterDetailsFormPage(AbstractFormEditor editor, String id,
			String title) {
		super(editor, id, title);
		block = createMasterDetailsBlock();
	}

	public AbstractMasterDetailsFormPage(String id, String title) {
		super(id, title);
		block = createMasterDetailsBlock();
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		block.createContent(managedForm);
	}

	protected abstract AbstractMasterDetailsBlock createMasterDetailsBlock();
}
