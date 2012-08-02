package com.mongodb.tools.shell;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoURI;
import com.mongodb.tools.driver.pagination.SortOrder;

public class ShellScriptBuilder {

	public static String connect(MongoURI mongoURI) {
		StringBuilder command = new StringBuilder("connect ");
		command.append(mongoURI);
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

	public static String collectionFind(String collectionName,
			Integer pageNumber, Integer itemsPerPage, String sortName,
			SortOrder order) {
		StringBuilder command = new StringBuilder("db.");
		command.append(collectionName);
		command.append(".find()");

		if (sortName != null && order != null) {
			DBObject orderBy = new BasicDBObject(sortName,
					(SortOrder.DESCENDING == order) ? -1 : 1);
			command.append(".sort(");
			// sort({name : 1, age : -1})
			// TODO manage sort!!!
			command.append(")");
		}
		if (pageNumber != null) {
			if (itemsPerPage != null) {
				// skip
				command.append(".skip(");
				command.append(String.valueOf((pageNumber) * itemsPerPage));
				command.append(")");
			}
			// limit
			command.append(".limit(");
			command.append(itemsPerPage);
			command.append(")");
		}
		return command.toString();
	}

	public static String showCollections() {
		return "show collections";
	}

	public static String dbAuthenticate(String username, char[] passwd) {
		StringBuilder command = new StringBuilder("db.authenticate(");
		command.append(username);
		command.append(",");
		command.append(passwd);
		command.append(")");
		return command.toString();
	}

}
