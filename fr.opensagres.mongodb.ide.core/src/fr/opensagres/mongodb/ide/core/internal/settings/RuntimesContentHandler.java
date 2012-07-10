package fr.opensagres.mongodb.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public class RuntimesContentHandler extends AbstractContentHandler<MongoRuntime> {

	public RuntimesContentHandler(Collection<MongoRuntime> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("runtime".equals(localName)) {
			MongoRuntime runtime = new MongoRuntime();
			runtime.setName(attributes.getValue("name"));
			runtime.setPath(attributes.getValue("path"));
			super.list.add(runtime);
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
