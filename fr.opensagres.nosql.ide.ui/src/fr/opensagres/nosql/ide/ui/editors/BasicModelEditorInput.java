package fr.opensagres.nosql.ide.ui.editors;

import fr.opensagres.nosql.ide.core.model.IModelIdentity;

public abstract class BasicModelEditorInput<Model extends IModelIdentity>
		extends ModelEditorInput<Model> {

	public BasicModelEditorInput(Model model) {
		super(model);
	}

	public String getName() {
		return getModel().getName();
	}

	public String getToolTipText() {
		return getModel().getLabel();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BasicModelEditorInput))
			return false;
		BasicModelEditorInput<Model> other = (BasicModelEditorInput<Model>) obj;
		Model model = getModel();
		if (model == null) {
			if (other.getModel() != null) {
				return false;
			}
			return true;
		}
		String modelId = model.getId();
		if (modelId == null) {
			if (other.getModel().getId() != null)
				return false;
		} else if (!modelId.equals(other.getModel().getId()))
			return false;
		return true;
	}

}
