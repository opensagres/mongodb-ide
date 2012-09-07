package fr.opensagres.nosql.ide.core.internal.settings;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.settings.RuntimesConstants;

public class RuntimesSettings extends AbstractSettings<IServerRuntime> {

	private static final RuntimesSettings INSTANCE = new RuntimesSettings();

	public static RuntimesSettings getInstance() {
		return INSTANCE;
	}

	@Override
	protected ContentHandler createContentHandler(
			Collection<IServerRuntime> list) {
		return new RuntimesContentHandler(list);
	}

	@Override
	protected String getXMLRootElementName() {
		return RuntimesConstants.RUNTIMES_ELT;
	}

	@Override
	protected void save(IServerRuntime t, Writer writer) throws IOException {
		t.save(writer);
	}

}
