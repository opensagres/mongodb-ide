package fr.opensagres.nosql.ide.core;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;

public interface IServerRuntimeListener {

	void runtimeAdded(IServerRuntime runtime);

	void runtimeRemoved(IServerRuntime runtime);

}
