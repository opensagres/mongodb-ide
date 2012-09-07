package fr.opensagres.nosql.ide.ui.handlers;

import org.eclipse.swt.widgets.Event;

public class ContextHandlerEvent<T> extends Event {

	private final T model;

	public ContextHandlerEvent(T model) {
		this.model = model;
	}

	public T getModel() {
		return model;
	}

}
