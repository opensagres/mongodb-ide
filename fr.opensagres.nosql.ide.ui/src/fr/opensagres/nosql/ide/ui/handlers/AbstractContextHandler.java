package fr.opensagres.nosql.ide.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public abstract class AbstractContextHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ContextHandlerEvent contextEvent = (ContextHandlerEvent) event.getTrigger();
		return execute(event, contextEvent);
	}

	protected abstract Object execute(ExecutionEvent event,
			ContextHandlerEvent contextEvent) throws ExecutionException;

}
