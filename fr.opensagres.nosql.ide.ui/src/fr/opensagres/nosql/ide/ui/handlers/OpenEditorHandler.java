package fr.opensagres.nosql.ide.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;

public abstract class OpenEditorHandler<T> extends AbstractContextHandler {

	@Override
	protected Object execute(ExecutionEvent event,
			ContextHandlerEvent contextEvent) throws ExecutionException {
		try {
			T model = (T)contextEvent.getModel();
			ContextHandlerUtils.openEditor(event, createEditorInput(model),
					getEditorId(), true);
		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	protected abstract String getEditorId();

	protected abstract IEditorInput createEditorInput(T model);

}
