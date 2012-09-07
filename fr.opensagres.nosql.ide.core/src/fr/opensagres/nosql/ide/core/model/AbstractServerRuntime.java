package fr.opensagres.nosql.ide.core.model;

import java.io.IOException;
import java.io.Writer;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.settings.RuntimesConstants;
import fr.opensagres.nosql.ide.core.utils.XMLUtils;

public abstract class AbstractServerRuntime implements IServerRuntime {

	private final String id;
	private String name;
	private String installDir;
	private final IServerType serverType;

	public AbstractServerRuntime(String serverTypeId, String name, String path)
			throws Exception {
		this(serverTypeId, String.valueOf(System.currentTimeMillis()), name,
				path);
	}

	public AbstractServerRuntime(String serverTypeId, String id, String name,
			String path) throws Exception {
		this.id = id;
		setName(name);
		setInstallDir(path);
		serverType = Platform.getServerTypeRegistry().getType(serverTypeId);
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

	public void setName(String name) {
		this.name = name;
	}

	public String getInstallDir() {
		return installDir;
	}

	public void setInstallDir(String installDir) throws Exception {
		validateInstallDir(installDir);
		this.installDir = installDir;
	}

	public final IServerType getServerType() {
		return serverType;
	}

	public void save(Writer writer) throws IOException {
		writer.append("<");
		writer.append(RuntimesConstants.RUNTIME_ELT);
		XMLUtils.writeAttr(RuntimesConstants.ID_ATTR, this.getId(), writer);
		XMLUtils.writeAttr(RuntimesConstants.NAME_ATTR, this.getName(), writer);
		XMLUtils.writeAttr(RuntimesConstants.PATH_ATTR, this.getInstallDir(),
				writer);
		// @type-id attribute
		XMLUtils.writeAttr(RuntimesConstants.SERVER_TYPE_ID_ATTR, this
				.getServerType().getId(), writer);
		doSave(writer);
		writer.append("/>");
	}

	protected void doSave(Writer writer) throws IOException {

	}

	protected abstract void validateInstallDir(String installDir)
			throws Exception;

}
