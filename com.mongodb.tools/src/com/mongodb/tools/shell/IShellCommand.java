package com.mongodb.tools.shell;

public interface IShellCommand {

	public static final int SHELL_CONNECTED = 0x0010;
	public static final int SHELL_DISCONNECTED = 0x0011;
	public static final int SHELL_SHOW_DBS = 0x0012;
	public static final int SHELL_USE = 0x0013;
	public static final int SHELL_SHOW_COLLECTIONS = 0x0014;
	public static final int SHELL_COLLECTION_FIND = 0x0015;
	
	int getKind();

	String getCommand();

	void execute();
}
