package com.mongodb.tools.shell;

public class ShellScriptBuilder {

	public static String connect(String host, Integer port) {
		StringBuilder command = new StringBuilder("connect ");
		command.append(host);
		if (port != null) {
			command.append(":");
			command.append(port);
		}
		return command.toString();
	}

	public static String preprocess(String script) {
		if (script == null || script.length() == 0)
			return script;
		if (script.indexOf("use ") != -1) {
			script = script.replaceAll("use +(\\w+)", "use('$1')");
		}
		if (script.indexOf("show dbs") != -1) {
			script = script.replaceAll("show dbs", "showDbs()");
		}
		if (script.indexOf("show collections") != -1) {
			script = script.replaceAll("show collections", "showCollections()");
		}
		if (script.indexOf("connect ") != -1) {
			script = script.replaceAll("connect +(\\w+)", "connect('$1')");
		}
		if (script.indexOf("disconnect ") != -1) {
			script = script.replaceAll("disconnect", "disconnect()");
		}
		return script;
	}

	public static String disconnect() {
		return "disconnect";
	}

	public static String showDbs() {
		return "show dbs";
	}

	public static String use(String name) {
		StringBuilder command = new StringBuilder("use ");
		command.append(name);
		return command.toString();
	}

	public static String showCollections() {
		return "show collections";
	}

}
