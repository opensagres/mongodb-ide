package com.mongodb.tools.driver.pagination;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.tools.shell.ShellCommandManager;
import com.mongodb.tools.shell.SysoutShellListener;

public class ShellCommandManagerTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		ShellCommandManager manager = ShellCommandManager.getInstance();
		manager.addShellListener(SysoutShellListener.getInstance());
		
		Mongo mongo = manager.connect("localhost", null);
		
		manager.disconnect(mongo);
	}
}
