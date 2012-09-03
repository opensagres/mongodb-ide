package fr.opensagres.nosql.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractContentHandler<T> extends DefaultHandler {

	protected final Collection<T> list;

	public AbstractContentHandler(Collection<T> list) {
		this.list = list;
	}
}
