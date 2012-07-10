package fr.opensagres.mongodb.ide.core.internal.settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class AbstractSettings<T> {

	public void load(InputStream stream, Collection<T> list)
			throws SAXException, IOException {
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		ContentHandler contentHandler = createContentHandler(list);
		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(new InputSource(stream));
	}

	public void save(Collection<T> list, Writer out) throws SAXException,
			IOException {
		try {
			String root = getXMLRootElementName();
			out.append("<");
			out.append(root);
			out.append(">");
			for (T t : list) {
				save(t, out);
			}
			out.append("</");
			out.append(root);
			out.append(">");
		} finally {
			out.flush();
			out.close();
		}
	}

	protected abstract ContentHandler createContentHandler(Collection<T> list);

	protected abstract String getXMLRootElementName();

	protected abstract void save(T t, Writer out) throws IOException;

	protected void writeAttr(String name, Integer value, Writer writer)
			throws IOException {
		writeAttr(name, value != null ? value.toString() : "", writer);
	}

	protected void writeAttr(String name, String value, Writer writer)
			throws IOException {
		writer.append(" ");
		writer.append(name);
		writer.append("=\"");
		writer.append(value);
		writer.append("\"");
	}

}
