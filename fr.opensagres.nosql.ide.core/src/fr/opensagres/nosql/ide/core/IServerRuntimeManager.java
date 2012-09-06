package fr.opensagres.nosql.ide.core;

import java.util.List;

import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;

public interface IServerRuntimeManager extends ISettingsManager {

	List<IServerRuntime> getRuntimes();

	List<IServerRuntime> getRuntimes(IServerType serverType);

	void addRuntime(IServerRuntime runtime) throws Exception;

	void removeRuntime(IServerRuntime runtime) throws Exception;

	IServerRuntime findRuntime(String runtimeId);

	void setRuntimes(IServerRuntime[] runtimes) throws Exception;
}
