package fr.opensagres.mongodb.ide.ui.editors;

public abstract class ModelEditorInput<Model> extends AbstractEditorInput {

	private final Model model;

	public ModelEditorInput(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}
}
