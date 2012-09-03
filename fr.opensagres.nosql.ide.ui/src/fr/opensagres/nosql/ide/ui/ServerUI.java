package fr.opensagres.nosql.ide.ui;


public class ServerUI {

	private static final String[] LOCALHOSTS = new String[] { "localhost",
			"127.0.0.1" };

	private static final String[] DEFAULT_PORTS = new String[] { "27017" };

	public static String[] getLocalhosts() {
		return LOCALHOSTS;
	}

	public static String[] getDefaultPorts() {
		return DEFAULT_PORTS;
	}
}
