package fr.opensagres.nosql.ide.core.utils;

import java.io.IOException;
import java.io.Writer;

public class XMLUtils {

	public static void writeAttr(String name, Integer value, Writer writer)
			throws IOException {
		writeAttr(name, value != null ? value.toString() : "", writer);
	}

	public static void writeAttr(String name, String value, Writer writer)
			throws IOException {
		writer.append(" ");
		writer.append(name);
		writer.append("=\"");
		writer.append(value);
		writer.append("\"");
	}
}
