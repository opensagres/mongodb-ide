package fr.opensagres.mongodb.ide.ui.editors.document;

import com.mongodb.DBObject;
import com.mongodb.tools.driver.DBObjectHelper;

import fr.opensagres.mongodb.ide.ui.editors.ModelEditorInput;

public class DocumentEditorInput extends ModelEditorInput<DBObject> {

	public DocumentEditorInput(DBObject model) {
		super(model);
	}

	public String getName() {
		return DBObjectHelper.getId(getModel());
	}

	public String getToolTipText() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DocumentEditorInput))
			return false;
		DocumentEditorInput other = (DocumentEditorInput) obj;
		DBObject document = getModel();
		if (document == null) {
			if (other.getModel() != null) {
				return false;
			}
			return true;
		}
		String otherId = DBObjectHelper.getId(other.getModel());
		String objectId = DBObjectHelper.getId(document);
		if (objectId == null) {
			if (otherId != null)
				return false;
		} else if (!objectId.equals(otherId))
			return false;
		return true;
	}

}
