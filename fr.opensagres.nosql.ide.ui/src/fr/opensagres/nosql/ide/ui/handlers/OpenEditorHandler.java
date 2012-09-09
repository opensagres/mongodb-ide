package fr.opensagres.nosql.ide.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.editors.AbstractEditorInput;

public abstract class OpenEditorHandler<T> extends AbstractContextHandler {

	@Override
	protected Object execute(ExecutionEvent event,
			ContextHandlerEvent contextEvent) throws ExecutionException {
		try {
			T model = (T) contextEvent.getModel();
			IEditorInput input = createEditorInput(model);
			IEditorPart part = ContextHandlerUtils.openEditor(event, input,
					getEditorId(), true);
			if (input instanceof AbstractEditorInput) {
				String activePageIdOnLoad = ((AbstractEditorInput) input)
						.getActivePageIdOnLoad();
				if (StringUtils.isNotEmpty(activePageIdOnLoad)) {
					((FormEditor) part).setActivePage(activePageIdOnLoad);
				}
			}

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	protected abstract String getEditorId();

	protected abstract IEditorInput createEditorInput(T model);

}
