package fr.opensagres.mongodb.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.mongodb.tools.process.InvalidMongoHomeDirException;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public class RuntimesContentHandler extends
		AbstractContentHandler<MongoRuntime> {

	public RuntimesContentHandler(Collection<MongoRuntime> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (RuntimesConstants.RUNTIME_ELT.equals(localName)) {
			MongoRuntime runtime;
			try {
				runtime = new MongoRuntime(
						attributes.getValue(RuntimesConstants.ID_ATTR),
						attributes.getValue(RuntimesConstants.NAME_ATTR),
						attributes.getValue(RuntimesConstants.PATH_ATTR));
				super.list.add(runtime);
			} catch (InvalidMongoHomeDirException e) {
				e.printStackTrace();
			}
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
