package fr.opensagres.mongodb.ide.ui.editors.server;

import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.editors.AbstractEditorInput;

public class ServerEditorInput extends AbstractEditorInput {

	private final Server server;

	public ServerEditorInput(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

	public String getName() {
		return getServer().getName();
	}

	public String getToolTipText() {
		return getServer().getLabel();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ServerEditorInput))
			return false;
		ServerEditorInput other = (ServerEditorInput) obj;
		if (server == null) {
			if (other.getServer() != null) {
				return false;
			}
			return true;
		}
		String serverId = server.getId();
		if (serverId == null) {
			if (other.getServer().getId() != null)
				return false;
		} else if (!serverId.equals(other.getServer().getId()))
			return false;
		return true;
	}

}
