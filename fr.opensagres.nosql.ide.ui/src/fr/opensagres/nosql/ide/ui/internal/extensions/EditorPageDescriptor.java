package fr.opensagres.nosql.ide.ui.internal.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IEditorPart;

public class EditorPageDescriptor {

	private static final String CLASS_ATTR = "class";

	private final String pageId;
	private final String title;
	private final IConfigurationElement ce;

	public EditorPageDescriptor(String pageId, String title,
			IConfigurationElement ce) {
		this.pageId = pageId;
		this.title = title;
		this.ce = ce;
	}

	public IEditorPart createPage(IEditorPart editor) throws CoreException {
		return (IEditorPart) ce.createExecutableExtension(CLASS_ATTR);
	}

	public String getPageId() {
		return pageId;
	}

	public String getTitle() {
		return title;
	}

}
