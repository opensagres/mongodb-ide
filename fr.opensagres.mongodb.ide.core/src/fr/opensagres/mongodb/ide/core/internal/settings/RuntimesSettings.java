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
		return "runtimes";
	}
	
	@Override
	protected void save(MongoRuntime t, Writer writer) throws IOException {
		writer.append("<runtime");
		super.writeAttr("name", t.getName(), writer);
		super.writeAttr("path", t.getPath(), writer);
		writer.append("/>");

	}

}
