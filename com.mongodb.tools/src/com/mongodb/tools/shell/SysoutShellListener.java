package com.mongodb.tools.shell;

import java.util.List;
import java.util.Set;

import com.mongodb.tools.shell.commands.ShowCollectionsShellCommand;
import com.mongodb.tools.shell.commands.ShowDbsShellCommand;
import com.mongodb.tools.shell.commands.UseShellCommand;

public class SysoutShellListener implements IShellCommandListener {

	private static final IShellCommandListener INSTANCE = new SysoutShellListener();

	public static IShellCommandListener getInstance() {
		return INSTANCE;
	}

	public void commandAdded(IShellCommand command) {
		System.out.println(getText(command));
	}

	private String getText(IShellCommand command) {
		StringBuilder text = new StringBuilder("> ");
		text.append(command.getCommand());
		text.append("\n");
		switch (command.getKind()) {
		case IShellCommand.SHELL_CONNECTED:
			text.append("connected");
			break;
		case IShellCommand.SHELL_DISCONNECTED:
			text.append("disconnected");
			break;
		case IShellCommand.SHELL_SHOW_DBS:
			ShowDbsShellCommand showDbs = (ShowDbsShellCommand) command;
			List<String> names = showDbs.getDatabaseNames();
			for (String name : names) {
				text.append(name);
				text.append("\n");
			}
			break;
		case IShellCommand.SHELL_USE:
			UseShellCommand use = (UseShellCommand) command;
			String name = use.getName();
			text.append("switched to db ");
			text.append(name);
			text.append("\n");
			break;
		case IShellCommand.SHELL_SHOW_COLLECTIONS:
			ShowCollectionsShellCommand showCollections = (ShowCollectionsShellCommand) command;
			Set<String> collectionNames = showCollections.getCollectionNames();
			for (String collectionName : collectionNames) {
				text.append(collectionName);
				text.append("\n");
			}
			break;
		}
		return text.toString();
	}

}
