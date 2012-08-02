package com.mongodb.tools.driver.pagination;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.tools.shell.ShellCommandManager;
import com.mongodb.tools.shell.SysoutShellListener;

public class ShellCommandManagerTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		ShellCommandManager manager = ShellCommandManager.getInstance();
		manager.addShellListener(SysoutShellListener.getInstance());

		MongoURI mongoURI = new MongoURI("mongodb://localhost");
		Mongo mongo = manager.connect(mongoURI);

		manager.disconnect(mongo);
	}
}
