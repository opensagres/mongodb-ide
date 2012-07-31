package com.mongodb.tools.driver.pagination;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.MongoException;
import com.mongodb.tools.shell.ShellCommandManager;
import com.mongodb.tools.shell.ShellContext;
import com.mongodb.tools.shell.SysoutShellListener;

public class ShellScriptCommandManagerTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		ShellCommandManager manager = ShellCommandManager.getInstance();
		manager.addShellListener(SysoutShellListener.getInstance());

		ShellContext context = manager.createContext();

		try {
			context.executeCommand("connect localhost");
			context.executeCommand("show dbs");
			context.executeCommand("use test");
			context.executeCommand("show collections");
			context.executeCommand("disconnect ");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
