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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getXMLRootElementName() {
		return RuntimesConstants.RUNTIMES_ELT;
	}

	@Override
	protected void save(IServerRuntime runtime, Writer out) throws IOException {
		// TODO Auto-generated method stub

	}

}
