package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.collection;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.opensagres.nosql.ide.mongodb.ui.internal.ImageResources;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.editors.AbstractToolbarFormPage;

public class IndexesPage extends AbstractToolbarFormPage {

	public static final String ID = "indexes";

	public IndexesPage(CollectionEditor editor) {
		super(editor, ID, Messages.IndexesPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_INDEX_16);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {

	}

}
