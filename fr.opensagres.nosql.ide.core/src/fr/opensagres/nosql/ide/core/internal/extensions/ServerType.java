package fr.opensagres.nosql.ide.core.internal.extensions;

import fr.opensagres.nosql.ide.core.extensions.IServerType;

public class ServerType implements IServerType {

	private final String id;
	private final String name;

	public ServerType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return getName();
	}
}
