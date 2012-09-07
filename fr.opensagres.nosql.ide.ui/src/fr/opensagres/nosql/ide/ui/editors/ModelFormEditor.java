package fr.opensagres.nosql.ide.ui.editors;

import org.eclipse.ui.IEditorInput;

import fr.opensagres.nosql.ide.ui.dialogs.StackTraceErrorDialog;

public abstract class ModelFormEditor<EditorInput extends IEditorInput, Model>
		extends AbstractFormEditor<EditorInput> {

	private Model model;

	@Override
	protected void onAfterAddPages() {
		super.onAfterAddPages();

		// // Load model
		try {
			loadModel();
		} catch (Exception e) {
			StackTraceErrorDialog.openError(getSite().getShell(),
					"Error loading", "Error loading model", e);
		}
	}

	private void loadModel() throws Exception {
		model = onLoad(getEditorInput());
	}

	public Model getModelObject() {
		return (Model) model;
	}

	/**
	 * Implement onLoad to retrieve the model object by the editor input object.
	 */
	protected abstract Model onLoad(EditorInput input) throws Exception;

}
