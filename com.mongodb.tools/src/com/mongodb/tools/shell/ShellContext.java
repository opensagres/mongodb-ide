package com.mongodb.tools.shell;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class ShellContext {

	private DB db;
	private final ShellCommandManager manager;
	private Mongo mongo;

	public ShellContext(ShellCommandManager manager) {
		this.manager = manager;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public void executeCommand(String script) throws IOException {
		Context context = Context.enter();
		Scriptable scope = initScriptEngine(context, this);
		context.evaluateString(scope, ShellScriptBuilder.preprocess(script),
				"test", 0, null);
		Context.exit();
	}

	private Scriptable initScriptEngine(Context context,
			ShellContext shellContext) throws IOException {
		ImporterTopLevel scope = new ImporterTopLevel(context);
		// scope.put("console", scope, console);
		scope.put("shellContext", scope, shellContext);
		Mongo mongo = shellContext.getMongo();
		if (mongo != null) {
			scope.put("mongo", scope, mongo);
		}
		scope.put("manager", scope, manager);
		context.initStandardObjects();
		Reader in = new InputStreamReader(getClass().getResourceAsStream(
				"mongo-shell.js"));
		context.evaluateReader(scope, in, "<initfile>", 0, null);
		return scope;

	}

	public DB getDB() {
		return db;
	}

	public void setDB(DB db) {
		this.db = db;
	}

}
