package fr.opensagres.mongodb.ide.core.internal.settings;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public class RuntimesSettings extends AbstractSettings<MongoRuntime> {

	private static final RuntimesSettings INSTANCE = new RuntimesSettings();

	public static RuntimesSettings getInstance() {
		return INSTANCE;
	}

	@Override
	protected ContentHandler createContentHandler(Collection<MongoRuntime> list) {
		return new RuntimesContentHandler(list);
	}

	@Override
	protected String getXMLRootElementName() {
		return RuntimesConstants.RUNTIMES_ELT;
	}

	@Override
	protected void save(MongoRuntime t, Writer writer) throws IOException {
		writer.append("<");
		writer.append(RuntimesConstants.RUNTIME_ELT);
		super.writeAttr(RuntimesConstants.ID_ATTR, t.getId(), writer);
		super.writeAttr(RuntimesConstants.NAME_ATTR, t.getName(), writer);
		super.writeAttr(RuntimesConstants.PATH_ATTR, t.getInstallDir(), writer);
		writer.append("/>");

	}

}
